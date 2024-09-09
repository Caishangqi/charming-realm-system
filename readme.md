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

该插件提供每个玩家一个独立世界的功能，有效减少区块占用、卡顿、区块保存困难及档案损坏等问题。玩家可以自主管理自己的世界，或邀请其他玩家加入共同建造。
通过菜单，玩家可以管理领域的合作建设者，并选择是否将自己的领域展示到公共菜单中，以便其他玩家访问。**→** [原帖地址](https://www.minebbs.com/resources/selfhomemain-1-7-10-1-21.8693/)


<p align="center">
<img alt="Realm Select" src="https://github.com/user-attachments/assets/42b869de-d67b-4361-8075-499ec2de3a77">
</p>
<p align="center">
领域导航菜单和领域选择列表
</p>

<p align="center">
<img alt="Realm Create" src="https://github.com/user-attachments/assets/0ebe7793-5c1f-4a05-8622-7299f0bb5bbd">
</p>
<p align="center">
领域创建菜单选择列表 - 动态Title
</p>

## 特性

> *斜体* 内容代表原插件 `SelfHomeMain` 不同的特性,该版本目前更改原插件GUI的可读性和外观, 随着版本更新该插件会逐步修改其他可以改善的地方

- 创建玩家独立世界 （支持超平坦 / 默认生存 / 及其他设定的模板地图）
- 设置或更改独立世界出生点,信任名单
- 独立世界世界边界,并且可以使用货币升级范围大小
- 同步世界时间功能,季节等
- 设置独立世界最大的`Tiles`数量


- *用户界面缓存,插件的用户界面会缓存,下次打开无需重新生成*
- *领域设置面板,设置操作也可以由其他领域成员执行*
- *支持将创建的玩家领域统一放到文件夹下(playerrealms)管理*
- *支持离线玩家领域玩家头颅和领域名称(玩家称号前缀)展示*
- *分离出模板创建领域时的磁盘拷贝操作至线程池*
- *添加创建队列,默认5个线程处理玩家的领域创建请求*
- *模板选择菜单,支持动态界面Title刷新,按钮选择时自定义样式*
- *在指定菜单区域内展示玩家领域头像而非直接填充*
- *给予按钮不同的样式基于当前页数*
- *支持HexColor,使用 `#83c22d` 充当颜色代码*
- *GUI中的物品名称现在也可以使用占位符*
  ...

## 权限系统重置
目前正在重构权限系统,包含的5个基础权限组为 `Owner 所有者`, `Operator 管理者`,`Member 成员`,和 `Visitor 访问者` 以及 `Banned 被封禁` 

| 名称         | 建造 | 邀请 | 封禁 | 访问 | 设置可见性 |    最大晋升    | 设置出生点 |
|------------|:--:|----|:--:|:--:|:-----:|:----------:|:-----:|
| `Owner`    | ✅  | ✅  | ✅  | ✅  |   ✅   | `Operator` |   ✅   |
| `Operator` | ✅  | ✅  | ✅  | ✅  |   ✅   |  `Member`  |   ✅   |
| `Member`   | ✅  | ❌  | ❌  | ✅  |   ❌   |   `None`   |   ❌   |
| `Visitor`  | ❌  | ❌  | ❌  | ✅  |   ❌   |   `None`   |   ❌   |
| `Banned`   | ❌  | ❌  | ❌  | ❌  |   ❌   |   `None`   |   ❌   |

- 在领域主界面中你可以**右键**头颅图标打开该领域的设置面板 (如果你有权限的话),**左键**则打开你自己的领域设置面板 (如果有的话)
- 使用指令 `/realm invite/remove/ban <玩家名称> from <所有者名称>` 操作指定领域玩家`邀请`/`移除`/`封禁`操作
- 使用指令 `/realm invite/remove/ban <玩家名称>` 操作你的领域玩家`邀请`/`移除`/`封禁`操作
- 当使用 `/realm invite` 后默认给予的权限是 `Member`, 若需要更改你需要去设置面板进行设置
- 当使用 `/realm remove` 后该玩家的权限会变为 `Visitor`
- 当使用 `/realm ban` 后该玩家的权限会变为 `Banned`

>**危险行为**: 如果你希望你的 `Operator` 可以将其他成员的权限也提升至 `Operator` 则可以打开设置面板并启用面板中的 `Enable perm proliferation` 允许管理员 *权限增生*. 只不过不建议这样做

## 占位符

> 当前注册的占位符标识有2个分别是:`SelfHomeMain`和`realm`,这里只说明模块 `realm`
> 的占位符使用,关于占位符模块`SelfHomeMain`
> 的使用请请到 [原帖地址](https://www.minebbs.com/resources/selfhomemain-1-7-10-1-21.8693/)

| 占位符名称                         | 参数               | 介绍         |
|-------------------------------|------------------|------------|
| `%realm_world_player_<Name>%` | `<Name>` 领域创建者名称 | 当前领域内的在线玩家 |
| `%realm_time_created_<Name>%` | `<Name>` 领域创建者名称 | 领域创建时间     |

### 内部占位符
> 内部占位符通常在插件的GUI `gui_<name>.yml`和语言 `message_cn.yml`配置文件中使用,他们无法被placeholderAPI解析，也通常没有参数

| 占位符名称                    | 介绍              |
|--------------------------|-----------------|
| `{REALM_CURRENT_PLAYER}` | 当前领域内的在线玩家      |
| `{REALM_CURRENT_PLAYER}` | 领域创建时间          |
| `{REALM_PERMISSION}`     | 该玩家在领域的权限组      |
| `{REALM_NAME}`           | 领域名称 (完整名称+权限组) |

## 待更新

| 内容                      | 优先级 | 状态  |
|:------------------------|:---:|:---:|
| 更好看的UI适配                | 最高  | 95% |
| 插件热重载                   | 最高  | 50% |
| 将GUI内部的Action操作解耦       | 较高  | 10% |
| 玩家添加信任名单时不用在领域内执行       | 较高  |  ✅  |
| 领域配置成员在线时保持加载           | 正常  | 10% |
| PlaceholderAPI 支持玩家头颅展示 | 正常  |  ✅  |
| 更改配置文件记录创建者和创建时间        | 正常  |  ✅  |
| 领域列表基于页数自动判断按钮样式        | 正常  |  ✅  |
| 领域权限修改,信任GUI重置          | 正常  | 60% |
| 将创建GUI的逻辑操作适配 bungee    | 最低  | 📌  |



## 待修复
> 项目中关于Placeholder的代码有部分存在着问题,包括不限于在 `setPlaceholders(null, str);` 字符串时没有传入`OfflinePlayer`,这样做会导致绝大部分插件的Placeholder调用失败. 后续修复应该重写插件`API.java`中的分支并默认传入OfflinePlayer (无论何时)

- 插件无法进行热重载,出现 `Variable.java` 类的 `ClassNotDefine` `(错误)`
- 插件输出消息给玩的时自动进行placeholder的解析 `(跟进 → 懒得改)`
- 跟进领域创建gui中的创建判断,玩家拥有领域时无法打开界面 `(已修复)`
- 配置文件`GUI.yml` 中,若按钮 `CustomName` 相同则会触发相同脚本 `(以弃用)`
- 在gui菜单的物品lore中不支持调用luck-perms插件的占位符因为这些占位符需要读取在线/离线玩家实例 `(使用静态文本实现)`

## 特别说明

这是一个专门给服务器使用的插件，主要需求会依照服务器需求进行配置，您的需求可能会被延后，感谢谅解。

> This is a plugin specially used for the server. The main requirements will be configured according to the server
> requirements. Your requirements may be delayed. Thank you for your understanding.

您可能会注意到，本插件使用了一些其他服务器的游戏资源和设计。对此，您可能会感到疑惑甚至愤怒：“这难道不是抄袭吗？”
事实上，这些资源仅用于确保GUI设计的一致性和美观性。而且，这些资源并不会通过本项目传播或被获取，因此不会涉及版权或其他法律问题。

> You might notice that this plugin uses some game resources and designs from other servers. This may raise concerns or
> even anger, as you might think, "Isn't this plagiarism?" However, these resources are only used to ensure consistency
> and aesthetics in the GUI design. Moreover, these resources will not be distributed or obtained through this project,
> thus avoiding any copyright or legal issues.

部分材质来源于Minecraft服务器Origin Realm以及插件ItemsAdder。请注意，您不能以任何方式传播这些资源。

> Some materials are sourced from the Minecraft server Origin Realm and the plugin ItemsAdder. Please note that you are
> not permitted to distribute these resources in any way.