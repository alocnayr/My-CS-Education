--Team member names: Ryan Kornitsky, Anna Balin, Krish Shah-Nathwani

--1a
create or replace view TransportAndLabRequests AS
SELECT SR.requestID, SR.role, status, requesterEmployeeID, handlerEmployeeID, floor, shortName, locationTypeID, TR.patientID as TransportPatient, TR.itemID as TransportVehicle, LR.patientID as LabPatient, labTest
FROM LabRequest LR
 FULL OUTER JOIN (SELECT * 
                  FROM ServiceRequest 
                  WHERE role != 'MedEquip') SR on LR.requestID = SR.requestID
 FULL OUTER JOIN TransportRequest TR on SR.requestID = TR.requestID
 join Location on destinationID = Location.locationID
ORDER BY SR.requestID;

--1b

SELECT role, status, count(requestID)
FROM TransportAndLabRequests
GROUP BY ROLLUP (role, status)
ORDER BY role, status;

--2

set serveroutput on;

create or replace procedure EmployeeLocation (emp_firstName varchar2, emp_lastName varchar2) is
    cursor Cemp is 
    SELECT firstName, lastName, shortName, employeeID, username
    FROM Employee E
    JOIN Location L on E.locationID = L.locationID
    WHERE firstName = emp_firstName and lastName = emp_lastName;
    
    begin 
    For rec in Cemp Loop
        dbms_output.put_line(rec.firstname || ' ' || rec.lastName || ' located in ' || rec.shortName || '. EmployeeID: ' || rec.employeeID || '. Username: ' || rec.username || '.' );
    End Loop;
end;
/
exec EmployeeLocation('Adam', 'Spears');


--3

create or replace trigger TransportationEquipment 
before insert or update on TransportRequest
for each row
declare 
equipType varchar2(15);
begin
select equipmentType into equipType 
from medicalEquipment
join TransportRequest on TransportRequest.itemID = medicalEquipment.itemID;
if (equipType != 'WheelChair' or equipType != 'Recliner') then
raise_application_error(-2001, 'ERROR: The equipment type for the transportation request is not a recliner or wheelchair!');
end if;
end;
/

--4
create or replace trigger UniqueNPI 
before insert or update on Employee
for each row
declare
cursor checkNPI is select employee.npi
from employee;
begin 
for employee in checkNPI loop
if (:new.NPI = :old.NPI) then
raise_application_error(-99999, 'ERROR: New doctors NPI number already exists in the system');
end if;
end loop;
end;
/

--5
create or replace trigger DefaultXRayLocation
before insert on LabRequest
for each row
declare 
	locationType varchar2(4);
begin

	select locationTypeID into locationType 
	from serviceRequest 
        join Location on serviceRequest.destinationID = location.locationID
	where requestID = :new.requestID;

	if(:new.labTest = 'XRay' and locationType != 'Labs') then
        update ServiceRequest set
        destinationID = 'LABS002L1'
        where requestId = :new.requestID;
    end if;
end;
/


