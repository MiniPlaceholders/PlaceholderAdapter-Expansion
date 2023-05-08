# PlaceholderAPI-Expansion
PlaceholderAPI Expansion for MiniPlaceholders

## Supported Platforms

- Paper
- Folia
- Velocity

### If you are using Velocity

In order to use PlaceholderAPI placeholders with MiniPlaceholders Velocity,
you must install [PAPIProxyBridge](https://modrinth.com/plugin/papiproxybridge) according to its installation guide

## Placeholders

- `<placeholderapi_global:(placeholder)>`

This placeholder does not require any player to work.
Its operation would be similar to running the command `/papi parse --null (placeholders)`

- `<placeholderapi_player:(placeholder)>`

This placeholder requires an online player to function.
Its operation would be similar to running the `/papi parse me (placeholders)` command

- `<placeholderapi_relational:(placeholder)>`

This placeholder requires two online players to work.
The functioning of this placeholder depends on whether the plugin in which it is used has relational placeholder support

If you want to nest the result of the placeholder inside another placeholder, add the string argument.
For example: `<placeholderapi_player:%player_name%:string>`

## Downloads

[![](https://raw.githubusercontent.com/Prospector/badges/master/modrinth-badge-72h-padded.png)](https://modrinth.com/plugin/miniplaceholders-placeholderapi-expansion)
