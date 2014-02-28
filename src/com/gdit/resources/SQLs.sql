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
