package cn.xxm.netty;

import cn.xxm.WeChatApplication;
import com.github.tobato.fastdfs.domain.GroupState;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.service.TrackerClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeChatApplication.class)
public class FastdfsClientApplicationTests {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private TrackerClient trackerClient;

    @Test
    public void contextLoads() {
        List<GroupState> groupStates = trackerClient.listGroups();
        for (GroupState groupState : groupStates) {
            System.out.println(groupState);
        }

    }

    /**
     * 测试文件上传
     */
    @Test
    public void upload() {

        try {
            //File file = new File("D:\\ds.jpg");
            File file = new File("D:\\cat.png");
            // 上传并且生成缩略图
            StorePath storePath = storageClient.uploadFile(
                  new FileInputStream(file),file.length(),"jpg",null);
            // 带分组的路径
            System.out.println(storePath.getFullPath());
            // 不带分组的路径
            System.out.println(storePath.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试上传缩略图
     */
    @Test
    public void uploadCrtThumbImage() {
        try {
            File file = new File("d:\\ds.jpg");

            FileInputStream inputStream = new FileInputStream(file);
            // 测试上传 缩略图
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, file.length(), "jpg", null);

            System.out.println(storePath.getGroup() + "" + storePath.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试文件下载
     */
    @Test
    public void download() {
        try {
            byte[] bytes = storageClient.downloadFile("group1", "M00/00/00/rBJsUFu-EkWABUFgAAJmrP3vP44153.jpg", new DownloadByteArray());

            FileOutputStream stream = new FileOutputStream("a.jpg");

            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
    * 测试文件删除
     */
    @Test
    public void deleteFile(){
        storageClient.deleteFile("group1","M00/00/00/rBJsUFu-DSaAKjUOAAJmrP3vP44055.jpg");
    }
}
