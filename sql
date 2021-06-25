CREATE TABLE doctor (
id serial primary key,			
name VARCHAR(10) NOT NULL,			
patronymic VARCHAR(30) NULL,			
surname VARCHAR(15) NOT NULL,			
phone VARCHAR(20) unique NOT NULL			
);

CREATE TABLE client(
id serial primary key,			
name VARCHAR(10) NOT NULL,			
patronymic VARCHAR(30) NOT NULL,			
surname VARCHAR(15) NOT NULL,			
phone VARCHAR(20) unique NOT NULL,			
position VARCHAR(30) NOT NULL,			
category VARCHAR(30) NOT NULL,			
workingHours VARCHAR(15) NOT NULL						
);

CREATE TABLE service(
id serial primary key,			
category VARCHAR(100) NOT NULL,			
name VARCHAR(100) NOT NULL,			
price  NUMERIC(9,2) NOT NULL,			
id_category serial NOT NULL				
);

CREATE TABLE timetable(
id serial primary key,			
data date,			
time time,			
id_client int not null references clients(id),			
id_doctor int not null references doctors(id),			
id_service int not null references services(id)
);
