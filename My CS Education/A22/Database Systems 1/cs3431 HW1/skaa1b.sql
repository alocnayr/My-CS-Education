--2a
select artist.firstname || ' ' || artist.lastname as "ArtistName", city || ', ' || state AS "Location"
from artist
where artist.state = 'CT' OR artist.state = 'MA'
order by artist.state, artist.city, artist.lastname;

--2b
update TicketPrice set ticketPrice = ticketPrice + 10
where year >= 2013 AND year <= 2021;
delete from TicketPrice 
where year = '2022';

select * from TicketPrice;

--2c
select building, gallery, title, price, to_char(purchasedate, 'MON DD, YYYY') as "PurchasedDate"
from gallery
natural join artwork
where (purchased = 'y') OR ((gallery = 'Sculpture Garden') AND (building = 'SKAA'))
order by building, gallery, title;

--2d
select artist.firstname, artist.lastname, artwork.title, materials.medium, (artwork.price * 2) as "PandemicPrice", gallery.gallery
from artwork
join artist on artwork.artistID = artist.artistID
join materials on artwork.materialsID = materials.materialsID
join gallery on artwork.gallery = gallery.gallery
where (artwork.price >= 100 AND artwork.price <= 200) AND (materials.category = 'Oil') AND ((gallery.gallery = 'Gund') OR (gallery.gallery = 'Walker'))
order by artist.lastname, artist.firstname, artwork.title;

--2e
select distinct gallery.gallery, materials.category
from gallery
join artwork on gallery.gallery = artwork.gallery 
join materials on materials.materialsID = artwork.materialsID
where gallery.building = 'Stein Conservatory'
order by gallery.gallery, materials.category;









