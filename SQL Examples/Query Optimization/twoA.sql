select sum(price) from (select language, price from stl.book order by language) group by language;