-- Project 2 Solutions
-- Note: There are alternate correct solutions to the following problems.

-- 1. TransportAndLabRequests
create or replace view TransportAndLabRequests as
select SR.requestID, SR.role, status, requesterEmployeeID, handlerEmployeeID, 
    floor, shortName, L.locationTypeID,
    TR.patientID as TransportPatient, itemID as TransportVehicle, 
    LR.patientID as LabPatient, labTest
from ServiceRequest SR
    join Location L on L.locationID = SR.destinationID
    left join TransportRequest TR on TR.requestID = SR.requestID
    left join LabRequest LR on LR.requestID = SR.requestID
order by SR.requestID;


select status, count(TransportPatient), count(LabPatient)
from TransportAndLabRequests
group by status
order by status;

-- 2. EmployeeLocation Procedure
set serveroutput on;
create or replace procedure EmployeeLocation (inFirstName IN varchar2, inLastName IN varchar2) IS   
     cursor E1 is
    	select employeeID, username, shortName
	from Employee E
		join Location L on E.locationID = L.locationID
	where firstName = inFirstName
		and lastName = inLastName;
begin
    for erec in E1 loop
        dbms_output.put_line(inFirstName || ' ' || inLastName 
            || ' located in ' || erec.shortName
            || '. EmployeeID: ' || erec.employeeID 
            || '. Username: ' || erec.username || '.');
    end loop;
end;
/

-- There may be more than one person with the same first and last names
-- A cursor is needed.
-- There is second Joselyn Klein entered into the system
insert into Employee values (300, 'JKlein2', 'wander', 'Joselyn', 'Klein', null, null, 5742621950, 'DEPT00501');
exec EmployeeLocation('Joselyn', 'Klein');

-- 3. TransportationEquipment
create or replace trigger TransportationEquipment
before insert on TransportRequest
FOR EACH ROW
declare
    equipmentType varchar2(40);
begin
    select equipmentType into equipmentType
    from MedicalEquipment  
    where itemID = :new.itemID;
        
    if (equipmentType != 'Recliner' and equipmentType != 'WheelChair') then
        raise_application_error(-20001, 'ERROR: The equipment type for the transportation request is not a recliner or wheelchair!');
    end if;
end;
/

-- New transportation service request
insert into ServiceRequest values (20, 'Transport', 'Unassigned', 190, null, 'PATI00605');

-- Error because it is not a recliner or wheelchair
insert into TransportRequest values (20, 'Transport', 160, 'Bed34');

-- Works because it is a wheelchair
insert into TransportRequest values (20, 'Transport', 160, 'WChair02');


-- 4. UniqueNPI Trigger
create or replace trigger UniqueNPI
before insert on Employee
FOR EACH ROW
declare
    NPI number(10);
    cursor N1 is 
        select NPI
        from Employee
        where NPI is not null;    
begin
    for nrec in N1 loop
        if (:new.NPI = nrec.NPI) then
            raise_application_error(-20002, 'ERROR: New doctor''s NPI number already exists in the system');
        end if;
    end loop;
end;
/

-- The following insert gives an error because Jarod Norton has that NPI number.
insert into Employee values (200, 'TCustomer', 'password', 'Test', 'Customer', null, null, 3381556063, 'PATI00304');

-- The following insert works because the NPI number does not already exist in the system.
insert into Employee values (200, 'TCustomer', 'password', 'Test', 'Customer', null, null, 3381556064, 'PATI00304');

-- 5. DefaultXRayLocation
create or replace trigger DefaultXRayLocation
before insert on LabRequest
FOR EACH ROW
declare
    locationTypeID char(4);
begin
    select locationTypeID into locationTypeID
    from ServiceRequest SR join Location L on SR.destinationID = L.locationID
    where requestID = :new.requestID;
    
    if (locationTypeID != 'LABS' and :new.labTest = 'XRay') then
        update ServiceRequest
        set destinationID = 'LABS002L1'
        where requestID = :new.requestID;
    end if;
end;
/

-- A service request of type Lab is assigned to a non lab location
insert into ServiceRequest values (60, 'Lab', 'Unassigned', 110, null, 'PATI01804');

-- When an XRay lab test is requested, if the location is not a lab, it defaults to
-- the OutPatient Flouroscopy lab
insert into LabRequest values (60, 'Lab', 'XRay', 175);
