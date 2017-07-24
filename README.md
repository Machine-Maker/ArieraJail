# ArieraJail
Custom Bukkit plugin requested by BananaBiscuits

### Setting up Jails and Cells
##### Step 0:
Make sure you have WorldEdit installed. This plugin will give an error on server startup if it is not installed!

##### Step 1:
Select an area in the shape of a cube using the WorldEdit axe (use //wand to get one).

##### Step 2:
Create the jail with a custom name by running the command __`/aj createjail <jail>`__ after you have selected the jail area.

##### Step 3:
Set the release location, the location will players will be teleported to when they are released, with the command __`/aj setrelease <jailname>`__. If no jail exists with that name, you will receive an error message saying as much.

##### Step 4:
Create cells inside the jail with __`/aj createcell <jail> <cell>`__. You must specify the jail you want to create the cell for as well as a custom cell name. The name for the cell must be unique for each cell per jail.

##### Step 5:
For each cell you create, you must also specify a spawn point in a cell. This is pretty simple as all you have to do is stand inside the cell area and do __`/aj setcellspawn`__.

##### Step 6:
Once you've handcuffed a player, to get him into a cell you have to create a sign for each cell that displays information on the cell and who's inside. Place a sign and while your crosshairs are on the sign, do __`/aj createsign <jailname> <cellname>`__. If everything goes as planned, the sign should look something like this.
![alt text](http://i.imgur.com/jdZ5Kc3.png "Example Sign")

#### You're done!
The jail and cells are now setup and ready for use!

### Using handcuffs
To get a pair of handcuffs in your inventory do __`/handcuffs`__. With the handcuffs, right click on another player. You (and they) will receive a message when they are successfully handcuffed. While the player is handcuffed, they will be walking at half speed and if they get more than 4 blocks away from you, they are teleported to you. The default 4 block distance is configurable. When you arrive at the jail, find an unoccupied cell and right click the sign. When you hover over the sign you should get a message on the action bar telling you to click. When you click, all the items/armor on the player are removed (and stored) and they are teleported into the cell where they stay for a configurable amount of time. You can also choose to release them early by clicking on the sign with a player in the corresponding cell.

### Commands and Permissions
|Commands     | Aliases      |Description  |Permissions |
|:------------:|:------------:|:-----------:|:-----------:|
|`/aj createjail <jail>`|`(createj, cj)`|Creates a jail|`arierajail.createjail`|
|`/aj deletejail <jail>`|`(deljail, dj)`|Deletes a jail|`arierajail.deletejail`|
|`/aj createcell <jail> <cell>`|`(createc, cc)`|Creates a cell|`arierajail.createcell`|
|`/aj deletecell <jail> <cell>`|`(delcell, dc)`|Deletes a cell|`arierajail.deletecell`|
|`/aj setrelease <jail>`|`(setr, sr)`|Sets the release point|`arierajail.setrelease`|
|`/aj setcellspawn`|`(setcs, scs)`|Sets the cell spawn|`arierajail.setcellspawn`|
|`/aj reload <config-type>`|`(rel, r)`|Reloads configurations|`arierajail.reload`|
|`/aj jaillist`|`(jlist, jl)`|Lists jails and cells|`arierajail.jaillist`|
|`/handcuffs`|`(cuffs)`|Gives handcuffs|`arierajail.handcuffs`|

##### Standalone Permissions
|Permission|Description|
|:--------:|:---------:|
|`arierajail.putinjail`|Allows cops to put player in a cell|
|`arierajail.releasefromjail`|Allows cops to release a player|
|`arierajail.handcuffs.bypass`|Makes handcuffs not work on this player|
|`arierajail.handcuffs.use`|Allows the use of handcuffs on other players|
|`arierajail.playerquit.bypass`|Allows player to avoid quitting penalties|
