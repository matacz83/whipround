insert into WHIPROUND (id, goal, description) values (nextval('whip_seq'), 100, 'Sweter dla Pana Dariusza');
insert into WHIPROUND (id, goal, description) values (nextval('whip_seq'), 40, 'Karma dla DogDao');

insert into DONATION (id, whipround_id, amount, user_name, email) values (nextval('donat_seq'), 1, 1, 'Cezary', 'cezary@gmail.com');

commit;