CREATE TABLE Car(
  id varchar(255) PRIMARY KEY
);
CREATE TABLE Order_Form (
  id int NOT NULL AUTO_INCREMENT,
  create_time timestamp,
  end_time timestamp,
  status varchar(10),
  car_id varchar(255),
  PRIMARY KEY (id)
);
CREATE TABLE Parking_Lot_Order_Forms (
  parking_lot_id int,
  order_forms_id int,
  PRIMARY KEY (parking_lot_id,order_forms_id)
);
ALTER TABLE Order_Form ADD CONSTRAINT FK1 FOREIGN KEY (car_id) REFERENCES Car;
ALTER TABLE Parking_Lot_Order_Forms ADD CONSTRAINT FK2 FOREIGN KEY (parking_lot_id) REFERENCES Parking_Lot;
ALTER TABLE Parking_Lot_Order_Forms ADD CONSTRAINT FK3 FOREIGN KEY (order_forms_id) REFERENCES Order_Form;