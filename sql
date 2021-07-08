CREATE TABLE doctor (
id serial primary key,			
name VARCHAR(10) NOT NULL,			
patronymic VARCHAR(30) NULL,			
surname VARCHAR(15) NOT NULL,			
phone VARCHAR(20) unique NOT NULL			
);
INSERT INTO doctor ( phone, name, patronymic, surname, position, category, workingHours) VALUES('+38066-666-66-66', 'Михаил', 'Дэвидович', 'Островский', 'Врач стоматолог-хирург', 'нет','четные');
INSERT INTO doctor ( phone, name, patronymic, surname, position, category, workingHours) VALUES('+38063-333-33-33', 'Ольга', 'Васильевна', 'Каликина', 'Врач стоматолог-терапевт', 'Высшая','четные');
INSERT INTO doctor ( phone, name, patronymic, surname, position, category, workingHours) VALUES('+38095-555-55-55', 'Аяз', 'Эльханович', 'Марданов', 'Врач стоматолог-терапевт', 'К.М.Н.','нечетные');


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
INSERT INTO service(category,name, price, id_category) VALUES('Чистка зубов', 'Чистка ультразвуком', 900, 1);
INSERT INTO service(category,name, price, id_category) VALUES('Чистка зубов', 'Чистка ультразвуком + пескоструйная обработка', 1200, 1);
INSERT INTO service(category,name, price, id_category) VALUES('Чистка зубов', 'Глубокое фторирование (1 зуб)', 100, 1);
INSERT INTO service(category,name, price, id_category) VALUES('Чистка зубов', 'Глубокое фторирование (1 челюсть)', 1300, 1);
INSERT INTO service(category,name, price, id_category) VALUES('Чистка зубов', 'Покрытие бифлюоридом (1 зуб)', 100, 1);
INSERT INTO service(category,name, price, id_category) VALUES('Чистка зубов', 'Покрытие бифлюоридом (1 челюсть)', 1500, 1);
INSERT INTO service(category,name, price, id_category) VALUES('Лечение зубов', 'Консультация стоматолога', 50, 2);
INSERT INTO service(category,name, price, id_category) VALUES('Лечение зубов', 'Фотополимерные пломбы', 550, 2);
INSERT INTO service(category,name, price, id_category) VALUES('Лечение зубов', 'Пломбировка корнев.каналов 1 канала', 150, 2);
INSERT INTO service(category,name, price, id_category) VALUES('Лечение зубов', 'Пломбировка корнев.каналов 2 канала', 250, 2);
INSERT INTO service(category,name, price, id_category) VALUES('Лечение зубов', 'Пломбировка корнев.каналов 3 канала', 350, 2);
INSERT INTO service(category,name, price, id_category) VALUES('Лечение зубов', 'Цельнолитая коронка', 950, 2);
INSERT INTO service(category,name, price, id_category) VALUES('Лечение зубов', 'Культевая вкладка', 950, 2);
INSERT INTO service(category,name, price, id_category) VALUES('Удаление зубов', 'Простое удаление зуба', 650, 3);
INSERT INTO service(category,name, price, id_category) VALUES('Удаление зубов', 'Сложное  удаление зуба', 1700, 3);
INSERT INTO service(category,name, price, id_category) VALUES('Удаление зубов', 'Сложное  удаление зуба с PRF', 2200, 3);
INSERT INTO service(category,name, price, id_category) VALUES('Удаление зубов', 'Удаление ретинированного зуба', 2500, 3);
INSERT INTO service(category,name, price, id_category) VALUES('Удаление зубов', 'Удаление зуба (атипичное)', 2000, 3);
INSERT INTO service(category,name, price, id_category) VALUES('Удаление зубов', 'Удаление радикса', 450, 3);
INSERT INTO service(category,name, price, id_category) VALUES('Удаление зубов', 'Удаление экзостоза', 650, 3);


CREATE TABLE timetable(
id serial primary key,			
data date,			
time time,			
id_client int not null references clients(id),			
id_doctor int not null references doctors(id),			
id_service int not null references services(id)
);
