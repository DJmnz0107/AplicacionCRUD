Base de datos de la aplicación: 

create table Usuarios(
UUID VARCHAR2(50) NOT NULL,
nombre_usuario VARCHAR2(30) NOT NULL UNIQUE,
contraseña_usuario VARCHAR2(256) NOT NULL,

CONSTRAINT pk_usuarios PRIMARY KEY (UUID)
);

create table Tickets (
UUID VARCHAR2(50) NOT NULL,
numero_ticket VARCHAR2(4) NOT NULL,
titulo_ticket VARCHAR2(25) NOT NULL,
descripcion_ticket VARCHAR2(150) NOT NULL,
autor_ticket VARCHAR2(10) NOT NULL,
email_autor VARCHAR2(50) NOT NULL,
estado_ticket VARCHAR2(10) NOT NULL,

CONSTRAINT pk_tickets PRIMARY KEY (UUID)
);
