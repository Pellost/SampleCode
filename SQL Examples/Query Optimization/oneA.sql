select cust# as customer
from stl.customer
where cust# between 1 and 100 and country = 'Canada';