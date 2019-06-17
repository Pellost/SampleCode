select cust# from stl.customer c where exists (select * from stl.purchase p where p.qnty = 1 and book# = 3);
