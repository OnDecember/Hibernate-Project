# Hibernate Project

Problems with the film_id column of the film_text table. 
It is necessary to make a foreign key instead of a primary key

It is better to make the name field in the language table a varchar

The title field type in the film table is varchar(128) and in the film_text table it is the same field with the varchar(255) type, although the information is stored the same, the maximum length must be the same.

It is not clear why the information from the film_text table is duplicated in the film table.
You need to delete the title and description fields from the film table

I believe that the film_id field in the table should not have a primary key, since this is an auxiliary table and this field should refer to film_id from the film table