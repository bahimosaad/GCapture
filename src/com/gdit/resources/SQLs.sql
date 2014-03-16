update capture 
set deleted = 1
where parent_id IN (select id from capture where type = 1 and deleted = 1)
and    type =2  and deleted = 0


update  capture 
set deleted = 1 
where parent_id IN (select id from capture where type = 2 and deleted = 1)
and    type =3  and deleted = 0

select count(*) from CAPTURE.CAPTURE
where deleted = 0 and ((type = 3) OR (type = 2 and blancked = 1)) 


select * from capture where id in  (
select parent_id from capture where id in (
select  distinct parent_id from capture
where  type=3
group by name,parent_id
having count(parent_id)>1))
and deleted=0
order by id


delete from capture c  
where type = 2 and deleted = 0 and
(select count(*) from capture where parent_id = c.id) <=0

select count(*) ,rep_id,barcoded from capture 
where type = 1 and status = 2 and deleted = 0 
group by rep_id,barcoded
order by rep_id;

--update status 
update capture c
set status = (select status from capture where type = 1 and c.parent_id = id)
where type = 2 and status <5
 
update capture c
set status = (select status from capture where type =2 and c.parent_id = id)
where type = 3 

doc_id not in
(
select distinct doc_id  from DOCUMENT_DATA
where field_val is null
group by doc_id , field_val 
having count(doc_id)=5)

select path,(select parent_id from Capture where id = c.PARENT_ID),c.REP_ID,c.CATEGORY_ID from capture c
where parent_id IN 
(
    select distinct doc_id    from DOCUMENT_DATA
    where field_val is null 
    and doc_id IN ( 
        select distinct doc_id from USERS_AUDIT   
        where    module_id =  5 and to_char(audit_date,'YYYY-MM-dd') = '2014-01-27' 
        and user_id = 441
    )
    group by doc_id , field_val 
    having count(doc_id)=4
)


select u.USER_NAME, u.ID ,count(*) m from CAPTURE.USERS_AUDIT a ,users u
where u.ID = a.USER_ID and  module_id =  5 and to_char(audit_date,'YYYY-MM-dd') = '2014-01-27'
group by user_name ,u.ID

order by m