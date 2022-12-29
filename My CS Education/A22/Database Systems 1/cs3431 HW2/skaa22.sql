--2a
select building.city, gallery.gallery, count(*) as NumberOfArtworks
from building
join gallery on building.buildingName=gallery.building
join artwork on gallery.gallery=artwork.gallery
where (year=2009 or year=2013 or year=2019)
group by rollup(building.city, gallery.gallery)
order by city, gallery.gallery;

--2b 
select artist.artistid, artist.firstname, artist.lastname
from artist
join artwork on artist.artistid = artwork.artistid
where year=2012
minus
select artist.artistid, artist.firstname, artist.lastname
from artist
join newart on artist.artistid = newart.artistid
where year=2020
order by firstname, lastname;

--2c
select artwork.title, artwork.gallery, artwork.year, artwork.price
from artwork
where artwork.price > 75 AND artwork.materialsID in (select materials.materialsID
from materials
where medium like '%glass%')
order by artwork.title; 

--2d
select artwork.year, to_char(sum(artwork.price), '$999,999.99') as "TotalArtValue", to_char(count(chosen)*(ticketprice), '$999,999.99') as "TicketRevenue",
to_char((sum(artwork.price)-(count(chosen)*(ticketprice))), '$999,999.99') as "CustomerSurplus"
from artwork
join ticketprice on artwork.year = ticketprice.year
where artwork.chosen IS NOT NULL
group by artwork.year,ticketprice
order by year desc;

--2e
select firstname, lastname, sum(price) as "MaxDonatedValue"
from artwork
join artist on artwork.artistid = artist.artistid
group by firstname, lastname
having sum(price) = (select max(sum(price))
from artwork
group by artistID);


