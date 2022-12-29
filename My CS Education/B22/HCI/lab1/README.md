# Lab 1:  Introducing HTML5 and CSS3

In this lab we will cover some basic HTML5 and CSS, as well as ways to make your web app look and feel like a native app. 

# Your tasks:

Create an HTML5 web app for Bike WPI that looks and feels like a native app.

Use a mobile device (or simulator) to check the solution app [here](http://users.wpi.edu/~esolovey/cs3041/lab1/)

To start with, your app should consist of 2 files: index.html and style.css.  The content of index.html can be copied from the end of this document.

# Step-by-Step Tutorial

☑️ **Step 0:** Fork this repository ([Help](https://help.github.com/en/github/getting-started-with-github/fork-a-repo)) so that you have a copy of it in your GitHub account. 

☑️ **Step 1:** Clone your lab1 repository so that you have a local copy on your computer ([Help](https://github.com/cs3041-b22/lab0/blob/main/README.md#3-clone-and-edit-your-project))

☑️ **Step 2:** On your local computer, open Sublime Text (or your favorite text editor) and create two files: *index.html* and *style.css*.

☑️ **Step 3:** Fill in the content of index.html by copying and pasting the HTML 5 code [here](https://github.com/cs3041-b22/lab1/blob/main/README.md#Code-for-indexhtml)

☑️ **Step 4:** Study the HTML code. 

When you’re done, save your work. To view your work so far, click on the index.html in your project directory. A web browser will open and things should look something like this:

![](https://github.com/cs3041-b22/lab1/blob/main/img/image1.png)

☑️ **Step 5:** Next, let’s add some style rules for your app using CSS. 

If you are new to CSS, check this [tutorial on CSS syntax](https://www.w3schools.com/css/css_syntax.asp).

The first thing to do is attach a stylesheet to your index.html file. You can do that by adding this line in the `<head>` section of the file. 
```
<link rel="stylesheet" type="text/css" href="style.css"/>
```
Also, this next line makes your web app look a bit more native on an iPhone, but you’ll need a device to test it. Make the following additions and changes to the next line, so that it looks like this:
```
<meta name="viewport" content="width=device-width, user-scalable=no" />
```
☑️ **Step 6:** Now, working in style.css, let’s define some fonts and colors for your app. 

Start with the body and header style:
 ```
header {
  font-family: Futura, Trebuchet MS;
  font-weight: bold condensed;
  color: #AC2B37; 
  font-size: 25px;
}
```
Define your own rules for styling `<body>` and `<h1>`. 

For information about CSS colors check [this tutorial](http://www.tutorialspoint.com/css/css_colors.htm).
If you are interested, here are the [colors that WPI uses on official websites](https://www.wpi.edu/sites/default/files/docs/Offices/Marketing-Communications/WPI_Institutional_9-4-12.pdf)

### Checkpoint: 
To test your styled app, you will need to use a device or a simulator:

To test using your device:

1) Follow the instruction [here](https://github.com/cs3041-b22/lab0#4-create-your-gh-pages-branch) to create a new branch *gh-pages*. Make sure the new branch's content is the same as the *main* branch and then push your changes to your remote repository.

2) Use the following url scheme to access your web-page:

**Note:** Sometimes it might take a while for the latest version to refresh and load. 
```
https://yourGithubUsername.github.io/lab1/index.html
```

**Note:** If you are getting an error when trying to load your page
1. Navigate here: https://github.com/yourGithubUsername/lab1/settings/pages - Make sure to change yourGithubUsername to your github username.

2. It will ask you which branch you are working in for your project  (e.g. gh-pages).

3. Click save.

☑️ **Step 7:** Continue to style your app by defining some padding (https://www.w3schools.com/css/css_padding.asp). 
Add the following code to the style.css file.
```
#content {
    padding:3px;
}
```
Add padding rules to `<p>` `<h1>` and `<header>`

Also, we can center our image:
```
#WPI{
    /*center the image*/
    display: block; 
    width: 50%;
    margin-left: auto;
    margin-right: auto;}
```
☑️ **Step 8:** Now we can add rules to make the navigation items look more like native apps:
```
#main_nav ul { padding: 0; margin: 0; }

#main_nav li  {
  list-style-type: none; /* remove bullet points */ 
}

#main_nav li a {
   display: block;
   text-decoration: none; /* remove underline */
   border-radius: 10px;  /* rounded corners */
   margin: 6px;
   padding: 9px;
   color: #222;
   font-weight: bold;

   /* nice gradient shade on the buttons */
   background-image: linear-gradient(to bottom, rgba(255,255,255,0), rgba(255,255,255,1));

   border: 1px solid #666; /* dark outline */
}
```
☑️ **Step 9:** Save all of your work and [submit your local changes](https://github.com/cs3041-b22/lab0#3-clone-and-edit-your-project) to your online repository. Now, let’s also add some rules to change the default behavior of the buttons in our app so that you get visual feedback on push events. 
```
nav li:active {
  background-color: #ccc;
  background-image: none;
  box-shadow: inset 0 3px 3px rgba(0, 0, 0, .2);
}
```
☑️ **Step 10:** Finally, style the `<footer>` tag.

### Checkpoint: Test your web app on a device or a simulator. 
(See [Lab 0](https://github.com/cs3041-b22/lab0#set-up-testing-environment) for using Chrome Developer tools)
             
☑️ **Step 11:** Now, create 3 additional html files that will be hyperlinked through the buttons:
```
stores.html
maps.html
ride.html
```
Each of these files should contain at least a header and a footer and should have style that is consistent with the style we defined for *index.html*

For now, the content of these pages is not very important, we will continue to develop this application together over the next few labs.

☑️ **Step 12:** Save all of your work and push to GitHub

[Submit your local changes](https://github.com/cs3041-b22/lab0#3-clone-and-edit-your-project) to your online repository

☑️ **Step 13:** On Canvas, submit the following: 
- link to your GitHub repository
- link to your GitHub page

#### Code for index.html
```
<!DOCTYPE html>

<html>
<head>
    <title> BIKE WPI </title>
</head>

<body>
    <!-- HTML5 article tag for content -->
    <div id="content">
   
    <header>BIKE WPI</header>

   <!-- WPI image -->
   <img id="WPI" src="gompei.jpg" width="320" />

   <!-- H1 means 1st level heading -->
   <h1>Welcome to Bike WPI</h1>
    
   <!-- P stands for paragraph -->
   <p>We believe in bicycles! Everything you need to be a WPI Cyclist.</p>
   
   <!-- Navigation buttons -->
   <nav id="main_nav">
      <ul>
         <!-- LI stands for list item -->
         <li><a href="stores.html">Bike Stores </a></li>
         <li><a href="maps.html">Bike Maps </a></li>
         <li><a href="ride.html">Go for a Ride</a></li>
      </ul>
   </nav>

   <footer>CS3041: Human-Computer Interaction </footer>
    </div>
</body>
</html>
```

### Submit your work
On Canvas, submit the following:
- a link to your lab's GitHub repository 
- a link to your web page (see this [quick tutorial on how to make a GitHub page](https://github.com/cs3041-b22/lab0#4-create-your-gh-pages-branch)) 
