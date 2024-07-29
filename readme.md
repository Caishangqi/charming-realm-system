# Charming Realm

![project-title-bg](https://github.com/user-attachments/assets/624149f2-23cc-4ce7-968c-5eda04c7c752)

<p align="center">
<img src = "https://i.imgur.com/EF6t6WA.png" alt="">
</p>

<h4 align="center">基于插件 SelfHomeMain 的高版本领域系统移植版本</h4>
<p align="center">
<a href="https://www.codefactor.io/repository/github/caishangqi/charming-realm-system"><img src="https://www.codefactor.io/repository/github/caishangqi/charming-realm-system/badge" alt="CodeFactor" /></a>
<img alt="Lines of code" src="https://img.shields.io/tokei/lines/github/Caishangqi/charming-realm-system">
<img alt="Lines of code" src="https://img.shields.io/badge/Spigot-1.16.5 to 1.20.1-green">
<img alt="GitHub branch checks state" src="https://img.shields.io/github/checks-status/Caishangqi/charming-realm-system/master?label=build">
<img alt="GitHub code size in bytes" src="https://img.shields.io/github/languages/code-size/Caishangqi/charming-realm-system">
</p>

## 概述

使服务器达到每个玩家一个单独的世界的功能，减少区块占用卡顿，区块保存困难炸档等问题,玩家可以自己管理世界或邀请其他玩家加入世界一起建造. 玩家可以通过菜单来管理领域的合作建设者也可以选择是否将领域展示到公共菜单中以便其他玩家访问.


<p align="center">
<img alt="GitHub code size in bytes" src="https://github.com/user-attachments/assets/60d24316-0728-4d62-aec3-5552a66607f3">
</p>
<p align="center">
领域导航菜单和领域选择列表
</p>

## 特性
**加粗**内容代表原插件 `SelfHomeMain` 不同的特性,该版本目前更改原插件UI的可读性和外观
- 创建玩家独立世界 （支持超平坦 / 默认生存 / 及其他设定的模板地图）
- 设置或更改独立世界出生点,信任名单
- 独立世界世界边界,并且可以使用货币升级范围大小
- 同步世界时间功能,季节等
- 设置独立世界最大的`Tiles`数量

- **在指定菜单区域内展示玩家领域头像而非直接填充**
- **给予按钮不同的样式基于当前页数**
  ...

## 待更新

| 内容                      | 优先级 | 状态 |
|:------------------------|:---:|:--:|
| 更好看的UI适配                | 最高  | 📝 |
| PlaceholderAPI 支持玩家头颅展示 | 正常  | 📝 |
| 更改配置文件记录创建者和创建时间        | 正常  | 📌 |
| 领域列表基于页数自动判断按钮样式        | 正常  | ✅  |

## 协调系列 Homeward Species

| [Homeward Cooking 协调烹饪](https://github.com/Caishangqi/homeward-plugin-cooking) | Caishangqi | 1.18.2 |
|-----------------------|------------|--------|
| **[Homeward Brewing 协调酿造](https://github.com/Ba1oretto/Brewing)** |  **Ba1oretto**          | **1.18.2** |
| **[Homeward Libs 协调核心](https://github.com/Caishangqi/homeward-plugin-lib)**    | **Caishangqi** | **1.18.2** |
| **[Homeward InfoBar 协调浮窗](https://github.com/Caishangqi/homeward-plugin-infobar)** | **Caishangqi** | **1.18.2** |

## 特别说明 
这是一个专门给服务器使用的插件，主要需求会依照服务器需求进行配置，您的需求可能会被延后，感谢谅解。

_This is a plugin specially used for the server. The main requirements will be configured according to the server requirements. Your requirements may be delayed. Thank you for your understanding._

你也许发现了,该插件使用了很多"其他"服务器的游戏资源和设计.你或许会感到很气愤,"天呐!你这是抄袭".然而使用其他服务器的资源只是用来确保GUI设计,你也不能从该项目中得到这些资源(就意味着这些资源不会被传播)

_You might notice that this plugin uses some game resources and designs from other servers. This may raise concerns or even anger, as you might think, "Isn't this plagiarism?" However, these resources are only used to ensure consistency and aesthetics in the GUI design. Moreover, these resources will not be distributed or obtained through this project, thus avoiding any copyright or legal issues._

部分材质来自 Minecraft服务器 Origin Realm 以及插件 ItemsAdder, 你不可以以任何方式散布其资源

_Some materials are sourced from the Minecraft server Origin Realm and the plugin ItemsAdder. Please note that you are not permitted to distribute these resources in any way._