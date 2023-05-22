create table topicos(
    id bigint not null auto_increment,
    titulo varchar(100) not null unique,
    mensaje varchar(300) not null unique,
    fecha_creacion DATE not null ,
    estado varchar(50) not null,
    cursoId bigint not null,
    usuarioId bigint not null,
    primary key (id),
    foreign key (usuarioId) references usuarios(id),
    foreign key (cursoId) references cursos(id)
)