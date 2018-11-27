## Road Runner Blocks


Complete overhaul of the mod from ground up.  The Road Runner has been re-caffeinated!

Some key features are as follows below.

1. Ability to add up to 64 blocks!  (This is a hard sanity check for performance reasons.)
2. A GUI for on the fly config changes.
3. A whitelist of which entities shall have the modifiers applied to them.

Some other things coming soon...

1. A wider variety of entities on the white list.
2. Additional bonus modifies / features such as attack speed and knockback resistance.
3. Improving the GUI screen with graphics.
4. Possibly an on screen HUD indicator that a modifier is being applied to the player.

#### So how exactly does one configure a block?  Well,  here is a break down for you.

Each entry in config looks something like this:
`minecraft:concrete|movement:2.5`

Each part of the entry is seperated with a "|" (pipe) symbol.  The first part is always the block name.  Sorry,  no meta data support (yet.)

The next part is the primary,  and required, modifier.
`movement:2.5`

It is merely the keyword followed by a `:` (colon)  with a non-negative value after it.

