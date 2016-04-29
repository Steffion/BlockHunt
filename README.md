# ![Thanks to ColeRule for making this awesome banner!](http://g2f.nl/0qheg35 "Thanks to ColeRule for making this awesome banner!") 

##Introduction
Hide and seek is a popular game mode on Minecraft that allows you to hide as blocks and disguise as normal props from seekers. This plugin allows you to do that but with a lot more features added on such as: shops, stats etc...

This plugin has been heavily inspired from the gamemode "PropHunt", from the game, "Garry's Mod".


**Download and try this plugin out for yourself!**

##Downloads
|Branch|Plugin version|Compatiable Minecraft version|Build status|Download|
|:--|:--|:--|:--|:--|
|**Release (master)**|**v0.2.1**|**v1.9.2**|[![Build Status](http://jenkins.steffion.com/buildStatus/icon?job=BlockHunt)](http://jenkins.steffion.com/job/BlockHunt/)|[Bukkit Dev](http://dev.bukkit.org/bukkit-plugins/blockhunt/files/)|
|Snapshot (dev)|v0.2.2-SNAPSHOT|v1.9.2|[![Build Status](http://jenkins.steffion.com/buildStatus/icon?job=BlockHunt-dev)](http://jenkins.steffion.com/job/BlockHunt-dev/)|[Jenkins](http://jenkins.steffion.com/job/BlockHunt-dev/)|
|Old version (1.8)|v0.2.1-MC1.8.8|v1.8.8|[![Build Status](http://jenkins.steffion.com/buildStatus/icon?job=BlockHunt-1.8)](http://jenkins.steffion.com/job/BlockHunt-1.8/)|[Jenkins](http://jenkins.steffion.com/job/BlockHunt-1.8/)|
|Recode (recode)|v0.3.0-ALPHA|v1.9.2|[![Build Status](http://jenkins.steffion.com/buildStatus/icon?job=BlockHunt-recode)](http://jenkins.steffion.com/job/BlockHunt-recode/)|[Jenkins](http://jenkins.steffion.com/job/BlockHunt-recode/)|

### This plugin requires you to download and install two addinonal plugins! ([Lib's Disguises](https://www.spigotmc.org/resources/libs-disguises.81/) and [ProtocolLib](http://dev.bukkit.org/bukkit-plugins/protocollib/))

##Support
||Link|
|:--|:--|
|**Issues/Tickets on GitHub**|[Click Here](https://github.com/Steffion/BlockHunt/issues/)|
|**Telegram**|[Click Here](http://telegram.me/Steffion)|
|**Dev-Bukkit comments**|[Click Here](http://dev.bukkit.org/bukkit-plugins/blockhunt/#comments)|
|**E-mail**|[Click Here](mailto:steffion@icloud.com?subject=BlockHunt)|

##Features
* Custom wand for selection arena.
* Multiple arenas.
* **Join/Leave signs!**
* Arena full bypass.
* Configurable blocks.
* **Solid blocks like the Hives!**
* Enable commands per arena.
* Executing commands on win.
* **Shop with tokens!**
* Instant respawn.
* **_And more!_**

##MCStats
[![](http://api.mcstats.org/signature/blockhunt.png)](http://mcstats.org/plugin/BlockHunt/)

##Commands & Permissions
_**Note:** Instead of using /BlockHunt you could use:_
* /bh
* /hideandseek
* /seekandfind (from my old plugin)

<> = Required [] = Optional

|Command|Description|Permission||
|:--|:--|:--|:--|
|`/BlockHunt [info/i]`|Displays the plugin's info.|blockhunt.info|_All players have this permission from default._|
|`/BlockHunt <help/h>`|Shows a list of commands.|blockhunt.help|_All players have this permission from default._|
|`/BlockHunt <reload/r>`|Reloads all configs.|blockhunt.reload|blockhunt.admin|
|`/BlockHunt <join/j> <arenaname>`|Joins a BlockHunt game.|blockhunt.join|blockhunt.player|
|`/BlockHunt <leave/l>`|Leave a BlockHunt game.|blockhunt.leave|blockhunt.player|
|`/BlockHunt <list/li>`|Shows a list of available arenas.|blockhunt.list|blockhunt.player|
|`/BlockHunt <shop/sh>`|Opens the BlockHunt shop.|blockhunt.shop|blockhunt.player|
|`/BlockHunt <start/go> <arenaname>`|Forces an arena to start.|blockhunt.start|blockhunt.moderator|
|`/BlockHunt <wand/w>`|Gives you the wand selection tool.|blockhunt.create|blockhunt.admin|
|`/BlockHunt <create/c> <arenaname>`|Creates an arena from your selection.|blockhunt.create|blockhunt.admin|
|`/BlockHunt <set/s> <arenaname>`|Opens a panel to set settings.|blockhunt.set|blockhunt.moderator|
|`/BlockHunt <setwarp/sw> <lobby/hiders/seekers/spawn> <arenaname>`|Sets warps for your arena.|blockhunt.setwarp|blockhunt.moderator|
|`/BlockHunt <remove/delete> <arenaname>`|Deletes an Arena.|blockhunt.remove|blockhunt.admin|
|`/BlockHunt <tokens/t> <set/add/take> <playername> <amount>`|Change someones tokens.|blockhunt.tokens|blockhunt.admin|
||Able to join full games.|blockhunt.joinfull|blockhunt.moderator|
||Able to join/leave using join/leave signs.|blockhunt.joinsign|blockhunt.player|
||Able to create a join/leave sign.|blockhunt.signcreate|blockhunt.moderator|
||Gives you the BlockChooser.|blockhunt.shop.blockchooser|blockhunt.admin|
||Gives you the ability to do all commands in-game.|blockhunt.allcommands|Operators|

###Other special permissions
|Permission|Description|
|:--|:--|
|`blockhunt.*`|All BlockHunt permissions.|
|`blockhunt.player`|All player related permissions.|
|`blockhunt.moderator`|All moderator related permissions.|
|`blockhunt.admin`|All admin related permissions.|
|`*`|All permissions on your server, including BlockHunt's permissions.|

##How to set-up?
Use this video to manage your way through the set-up phase!

<a href="http://www.youtube.com/watch?feature=player_embedded&v=msPQ1UMiHWg
" target="_blank"><img src="http://img.youtube.com/vi/msPQ1UMiHWg/0.jpg" 
alt="" width="240" height="180" border="10" /></a>

**Thanks to [Koz4Christ](https://www.youtube.com/user/koz4christ/) for this video!**

##Donate
Do you have some spare money lying around? Support BlockHunt and donate, as this will give us more encouragement to produce new updates. It will also show us how interested people are in this plugin!

[![](https://www.paypalobjects.com/en_US/GB/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=RWASM5GV7GYTU)

Thank you! <3

## Try out recode 
I am currently recoding the plugin and I would love to know if you like it.
Suggest ideas or report bugs via the issues system.
Go to my Jenkins and download the latest snapshot version.

http://jenkins.steffion.com/job/BlockHunt-recode/

>**_Disclaimer_**
> This plugin uses an auto update system, which means it will check for updates (this feature is perhaps broken at the moment). To disable this go to the config file and disable the auto-update feature. This plugin uses a Metrics system made by Hidendra, which means some information will be sent to MCStats.org and shown to the public. This data includes information such as the plugin's version, server versions, OS systems, Java version, etc. All data is sent to MCStats.org for analysis either by the public or plugin author. No personal data is taken from the server and if you want to opt-out of MCStats, go to your plugins folder, then go to the "PluginMetrics" folder (This folder is automatically created) and then set "opt-out" to true. More info can be found on MCStats.org. With this information, we can see how many people are using the current version of the plugin and its popularity so we know when to push for further updates.
