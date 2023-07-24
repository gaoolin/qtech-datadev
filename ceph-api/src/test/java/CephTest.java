/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/14 15:25:15
 * desc   :  TODO
 */


import com.ceph.fs.CephMount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest(classes = CephTest.class)
@RunWith(SpringRunner.class)
public class CephTest {
/*    @RequestMapping(value = "/ceph/test", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Map<String, Object> cephTest(@RequestBody String jsonStr) {

        //删除测试时所创建的桶名称
        *//*String oldBucketName = "my-new-bucket";
        CephUtils.deleteBucket(oldBucketName);*//*

        String uuid = UUID.randomUUID().toString();
        //String fileName = "message_" + uuid + ".txt";
        uuid = uuid.replace("-", "");
        String fileName = uuid;
        String bucketName;
        //先查看远程ceph系统是否存在当前日期的桶名称 bst-yyyymmdd
        boolean isCreated = CephGrwServiceImpl.getBucketIsCreated();
        //如果返回true,证明当天的桶名称已存在；否则创建
        if (!isCreated) {
            //新创建桶名称
            CephGrwServiceImpl.createBucket();
        }
        bucketName = CephGrwServiceImpl.getCurrentDateBucketName();
        CephGrwServiceImpl.uploadByte(bucketName, fileName, jsonStr.getBytes());
        Map<String, Object> map = new HashMap<>();
        map.put("message", "成功=" + fileName);
        map.put("message", "成功=" + bucketName);
        return map;
    }*/

    @Test
    public void cephFsTest() {
        //admin是ceph的admin用户　
        CephMount mount = new CephMount("admin");
        //10.112.101.141;10.112.101.142;10.112.101.143是ceph集群的mon节点，有多少个写多少个
        mount.conf_set("mon_host", "10.96.179.58;10.96.141.70;10.96.137.48");
        System.out.println(mount.conf_get("mon_host"));
        //以下的key来自于ceph环境的/etc/ceph/ceph.client.admin.keyring里面的key
        mount.conf_set("key","AQDztv1j2RZSOxAAb+KA/gKvLu8e4P1GR+C3AQ==");
        //在创建目录之前必须先mount到根目录
        mount.mount("/");
        //在根目录下面创建子目录　mysqlDB,0777是对目录的权限控制，这个可以改成别的，不过最好要让目录具有读写权限
        try {
            mount.mkdirs("/mongoDB/",0777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建完后断掉mount
        mount.unmount();
        System.out.println("success");

    }
}
