select P.cust#, B.book#
from((stl.purchase P full outer join stl.customer C on P.cust# = C.cust#)
full outer join stl.book B on P.book# = B.book#)
where P.cust# in (select C1.cust# from stl.customer C1 where C1.cust# between 1 and 100 order by P.cust#);