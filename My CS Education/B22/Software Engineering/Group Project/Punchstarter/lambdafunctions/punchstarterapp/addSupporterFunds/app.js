// const axios = require('axios')
// const url = 'http://checkip.amazonaws.com/';
let response;
const mysql = require('mysql');

var config = require('./config.json');
var pool = mysql.createPool({
    host: config.host,
    user: config.user,
    password: config.password,
    database: config.database
});

function query(conx, sql, params) {
    return new Promise((resolve, reject) => {
        conx.query(sql, params, function(err, rows) {
            if (err) {
                // reject because there was an error
                reject(err);
            }
            else {
                // resolve because we have result(s) from the query. it may be an empty rowset or contain multiple values
                resolve(rows);
            }
        });
    });
}


/**
 *
 * Event doc: https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-proxy-integrations.html#api-gateway-simple-proxy-for-lambda-input-format
 * @param {Object} event - API Gateway Lambda Proxy Input Format
 *
 * Context doc: https://docs.aws.amazon.com/lambda/latest/dg/nodejs-prog-model-context.html 
 * @param {Object} context
 *
 * Return doc: https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-proxy-integrations.html
 * @returns {Object} object - API Gateway Lambda Proxy Output Format
 * 
 */
exports.lambdaHandler = async (event, context) => {

    let response = {
        headers: {
            "Access-Control-Allow-Headers": "Content-Type",
            "Access-Control-Allow-Origin": "*", // Allow from anywhere
            "Access-Control-Allow-Methods": "POST" // Allow POST request
        }
    }; // response

    let actual_event = event.body;
    let info = JSON.parse(actual_event);

    let email = info.Email_ID;
    let password = info.Password;
    let funds = info.Funds;


    //query database to see if there is a match
    // if match 200
    // anything else is 400
    let checkIsUser = (email, password) => {
        return new Promise((resolve, reject) => {

            pool.query("SELECT * FROM Supporter WHERE Email_ID=? and Password=?", [email, password], (error, rows) => {

                if (error) { return reject(error); }

                if ((rows) && (rows.length == 1)) {
                    return resolve(true); // TRUE if does exist
                }
                else {
                    return resolve(false); //no user with email 
                }
            });
        });
    };


    let updateFunds = (funds, email) => {
        return new Promise((resolve, reject) => {

            pool.query("update Supporter set Funds=Funds+? where Email_ID=?", [funds, email], (error, result) => {

                if (error) { return reject(error); }

                if (result.affectedRows == 1) {
                    return resolve(true); // TRUE correct user and password
                }
                else {
                    return resolve(false); //false incorrect password
                }
            });
        });
    };



    try {
        const isUser = await checkIsUser(email, password);
        if (isUser) {
            if((!isNaN(funds)) && (funds > 0)){
                const updatedFunds = await updateFunds(funds, email);
                if (updatedFunds) {
                    response.statusCode = 200;
                }
                else {
                    response.statusCode = 400;
                    response.error = "cant update funds";
                }
                    
            }else{
                response.statusCode = 400;
                response.error = "incorrect format of funds";
            }
        }
        else {
            response.statusCode = 400;
            response.error = "Incorrect Password";
        }
    }
    catch (err) {
        response.statusCode = 400;
        response.error = "something wrong" + err;
    }
    return response;
};
