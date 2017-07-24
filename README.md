# ArieraJail
Custom Bukkit plugin requested by BananaBiscuits

### Setting up Jails and Cells
##### Step 0:
Make sure you have WorldEdit installed. This plugin will give an error on server startup if it is not installed!

##### Step 1:
Select an area in the shape of a cube using the WorldEdit axe (use //wand to get one).

##### Step 2:
Create the jail with a custom name by running the command __`/aj createjail <jailname>`__ after you have selected the jail area.

##### Step 3:
Set the release location, the location will players will be teleported to when they are released, with the command __`/aj setrelease <jailname>`__. If no jail exists with that name, you will receive an error message saying as much.

##### Step 4:
Create cells inside the jail with __`/aj createcell <jailname> <cellname>`__. You must specify the jail you want to create the cell for as well as a custom cell name. The name for the cell must be unique for each cell per jail.

##### Step 5:
For each cell you create, you must also specify a spawn point in a cell. This is pretty simple as all you have to do is stand inside the cell area and do __`/aj setcellspawn`__.

##### Step 6:
Once you've handcuffed a player, to get him into a cell you have to create a sign for each cell that displays information on the cell and who's inside. Place a sign and while your crosshairs are on the sign, do __`/aj createsign <jailname> <cellname>`__. If everything goes as planned, the sign should look something like this.
![alt text](http://i.imgur.com/jdZ5Kc3.png "Example Sign")
