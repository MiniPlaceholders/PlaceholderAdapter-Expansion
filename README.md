# PlaceholderAdapter-Expansion
PlaceholderAdapter Expansion for MiniPlaceholders

## Supported Platforms

- Paper
- Velocity

### If you are using Velocity

In order to use PlaceholderAPI placeholders with MiniPlaceholders Velocity,
you must install [PAPIProxyBridge](https://modrinth.com/plugin/papiproxybridge) according to its installation guide

On Velocity there is an additional optional `expiry` parameter for how long the placeholder should be cached. This value is in milliseconds.

## Placeholders

- `<placeholder-adapter_global:(placeholder)>`
Velocity:
- `<placeholder-adapter_global:(placeholder):(expiry)>`

This placeholder does not require any player to work.
Its operation would be similar to running the command `/papi parse --null (placeholders)`

- `<placeholder-adapter_player:(placeholder)>`
Velocity:
- `<placeholder-adapter_player:(placeholder):(expiry)>`

This placeholder requires an online player to function.
Its operation would be similar to running the `/papi parse me (placeholders)` command

- `<placeholder-adapter_relational:(placeholder)>`
Velocity:
- `<placeholder-adapter_relational:(placeholder):(expiry)>`

This placeholder requires two online players to work.
The functioning of this placeholder depends on whether the plugin in which it is used has relational placeholder support

If you want to nest the result of the placeholder inside another placeholder, add the string argument.
For example:
- `<placeholder-adapter_papi_player:%player_name%:string>`
- `<placeholder-adapter_player:%player_name%:string:30000>`

## Downloads

[![](https://raw.githubusercontent.com/Prospector/badges/master/modrinth-badge-72h-padded.png)](https://modrinth.com/plugin/miniplaceholders-placeholderapi-expansion)
