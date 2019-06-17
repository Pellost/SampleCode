select A.cust#
from stl.purchase as A, stl.customer as B 
where A.cust# = B.cust# and B.cust# between 1 and 100 order by B.cust#;
