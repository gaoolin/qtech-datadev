#### GroupD和ArtifactID

　　GroupID 是项目组织唯一的标识符，实际对应JAVA的包的结构，是main目录里java的目录结构。

　　ArtifactID是项目的唯一的标识符，实际对应项目的名称，就是项目根目录的名称。

#### 命名规则：如果要把项目弄到maven本地仓库中，那项目就必须指定唯一的坐标，即指定唯一的GroupD和ArtifactID

GroupId一般分为多个段，第一段为域，第二段为公司名称。域又分为org、com、cn等等许多，其中org为非营利组织，com为商业组织。举个apache公司的tomcat项目例子：这个项目的GroupId是org.apache，它的域是org（因为tomcat是非营利项目），公司名称是apache，ArtifactId是tomcat。再比如我自己建项目，可以写com.leon.
ArtifactId 是项目名称，比如：apache的tomcat项目，再比如我自己的项目testProj