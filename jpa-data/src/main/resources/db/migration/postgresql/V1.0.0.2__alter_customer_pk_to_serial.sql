create sequence seq_customer_id start with 3 owned by customer.id;
alter table customer alter column id set default nextval('seq_customer_id');
