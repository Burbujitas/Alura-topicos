
	create table respuesta_topicos(
	id bigint not null auto_increment,
	mensaje varchar(300) not null,
    fecha_creacion date not null,
    solucion boolean not null,
    usuarioId bigint not null,
    topicoId bigint not null,
    primary key (id),
    foreign key(usuarioId) references usuarios(id),
    foreign key(topicoId) references topicos(id)
	)



