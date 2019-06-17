select P.cust#, B.book#
from ((stl.purchase P full outer join stl.customer C on P.cust# = C.cust#) full outer join stl.book B on P.book# = B.book#)
where P.cust# in (select C1.cust# from ((stl.customer C1 inner join stl.purchase P1 on P1.cust# = C1.cust#)
inner join stl.book B1 on B1.book# = P1.book#)
where C1.cust# between 1 and 100 order by C1.cust#);