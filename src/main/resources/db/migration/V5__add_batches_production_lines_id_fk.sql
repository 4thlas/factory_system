alter table batches
    add constraint batches_production_lines_id_fk
        foreign key (production_line_id) references production_lines (id)
            on update cascade;