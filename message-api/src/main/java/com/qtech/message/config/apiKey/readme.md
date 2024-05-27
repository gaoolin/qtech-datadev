#### 要发送一个合格的API请求，客户端需要遵循以下步骤：
1. API Key: 首先，客户端需要一个有效的API密钥。在 ApiKeyUserDetailsService 中，loadUserByUsername 方法会根据API密钥从 apiKeys 服务中查找对应的用户名。所以，客户端必须拥有一个已注册的API密钥。
2. 请求头: 在HTTP请求中，客户端需要在头信息中包含API密钥。头的名称是 X-API-Key。例如，如果API密钥是 "my_valid_api_key"，客户端应该这样设置请求头：
```bash
   X-API-Key: my_valid_api_key
```
3. 豁免路径: 如果请求的目标是豁免路径，例如 /message/api/list/** 或 /exempted/path2，客户端可以不提供API密钥。在这种情况下，Spring Security配置会允许这些请求通过，而不会触发API密钥验证。
4. URL和HTTP方法: 请求的URL和HTTP方法（GET、POST、PUT、DELETE等）应符合你的API设计。例如，如果豁免路径是 /message/api/list，并且支持GET请求，客户端可以发送这样的请求：
```bash
   GET /message/api/list HTTP/1.1
   Host: your-api-domain.com
   X-API-Key: my_valid_api_key
```
5. 认证和授权: 对于非豁免路径，如果API密钥有效并被验证，UsernamePasswordAuthenticationToken 将被创建并设置到 SecurityContextHolder 中。客户端可以通过API密钥获得相应的角色（例如，ROLE_ADMIN 或 ROLE_USER），这将决定他们可以访问哪些资源。
6. 错误处理: 如果API密钥无效或缺失，服务器会返回403 Forbidden或401 Unauthorized状态，并提供错误信息。客户端需要能够处理这些错误响应并显示适当的错误消息给用户。
**综上所述，客户端在发送请求时，应确保在 X-API-Key 请求头中包含有效的API密钥，除非请求的是豁免路径。如果请求需要特定的角色权限，客户端也需要确保API密钥对应的角色有访问资源的权限。**


#### 示例
1. 使用API密钥访问非豁免路径（例如：获取用户信息）
```bsh
GET /user/api/profile HTTP/1.1
Host: your-api-domain.com
X-API-Key: my_valid_api_key
```
在这个例子中，API密钥是必要的，因为 /user/api/profile 不是豁免路径。
2. 跳过API密钥的豁免路径请求（例如：获取公共消息列表）
```bash
GET /message/api/list HTTP/1.1
Host: your-api-domain.com
```
在这种情况下，由于 /message/api/list 是豁免路径，客户端可以直接发送请求，无需提供API密钥。
3. 使用无效API密钥的请求
```bash
GET /user/api/profile HTTP/1.1
Host: your-api-domain.com
X-API-Key: invalid_api_key
```
此请求将导致401 Unauthorized响应，因为API密钥无效。
4. 使用正确API密钥但无权限的请求
假设API密钥对应于 ROLE_USER 角色，但请求需要 ROLE_ADMIN 权限：
```bash
GET /admin/api/settings HTTP/1.1
Host: your-api-domain.com
X-API-Key: my_valid_api_key
```
这将返回403 Forbidden，因为API密钥对应的用户没有足够的权限访问 /admin/api/settings。
请注意，实际的API设计和权限模型可能会有所不同，以上示例仅供参考。