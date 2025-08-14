-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-08-11 13:24:14.778

-- tables
-- Table: client
CREATE TABLE client (
                        id int GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
                        first_name varchar(50)  NOT NULL,
                        last_name varchar(50)  NOT NULL,
                        nationality varchar(50)  NOT NULL,
                        phone varchar(20)  NULL,
                        CONSTRAINT client_pk PRIMARY KEY (id)
);

-- Table: reservation
CREATE TABLE reservation (
                             id int GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
                             room_id int  NOT NULL,
                             client_id int  NOT NULL,
                             check_in_date timestamp  NOT NULL,
                             check_out_date timestamp  NOT NULL,
                             nights int  NOT NULL,
                             total_price decimal(10,2)  NOT NULL,
                             CONSTRAINT reservation_pk PRIMARY KEY (id)
);

-- Table: room
CREATE TABLE room (
                      id int GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
                      room_type_id int  NOT NULL,
                      name varchar(100)  NOT NULL,
                      price decimal(10,2)  NOT NULL,
                      CONSTRAINT room_pk PRIMARY KEY (id)
);

-- Table: room_type
CREATE TABLE room_type (
                           id int GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
                           type_name varchar(50)  NOT NULL,
                           CONSTRAINT room_type_ak_1 UNIQUE (type_name),
                           CONSTRAINT id PRIMARY KEY (id)
);

-- foreign keys
-- Reference: reservation_client (table: reservation)
ALTER TABLE reservation ADD CONSTRAINT reservation_client
    FOREIGN KEY (client_id)
        REFERENCES client (id);

-- Reference: reservation_room (table: reservation)
ALTER TABLE reservation ADD CONSTRAINT reservation_room
    FOREIGN KEY (room_id)
        REFERENCES room (id);

-- Reference: room_room_type (table: room)
ALTER TABLE room ADD CONSTRAINT room_room_type
    FOREIGN KEY (room_type_id)
        REFERENCES room_type (id);