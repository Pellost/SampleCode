select cust# from stl.purchase where cust# in
(select cust# from stl.customer where cust# between 1 and 100 order by cust#);
