package com.qtech.ceph.rbd.service;

import com.ceph.rados.IoCTX;
import com.ceph.rados.exceptions.RadosException;
import com.ceph.rados.jna.Rados;
import com.ceph.rbd.RbdException;
import com.ceph.rbd.RbdImage;
import com.ceph.rbd.jna.Rbd;
import com.ceph.rbd.jna.RbdImageInfo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 11:10:33
 * desc   :
 */


/**
 * Implementation of the RbdService interface, managing RBD (RADOS Block Device) images.
 */
/*
@Service
public class RbdServiceImpl implements RbdService {

    private static final String MON_HOST = "172.16.60.41";
    private static final String KEY = "AQCdP9pYGI4jBBAAc96J8/OconCkVKWPBNU2vg==";
    private static final String POOL_NAME = "rbd";

    private static Rados rados;
    private static IoCTX ioctx;
    private static Rbd rbd;

    @Override
    public void connect() throws RadosException {
        try {
            rados = new Rados("admin");
            rados.confSet("mon_host", MON_HOST);
            rados.confSet("key", KEY);
            rados.connect();
            ioctx = rados.ioCtxCreate(POOL_NAME);
            rbd = new Rbd(ioctx);
        } catch (RadosException e) {
            throw new RadosException("Failed to connect to Ceph cluster.", e);
        }
    }

    @Override
    public List<String> listImages() throws RadosException {
        try {
            List<String> imageList = Arrays.asList(rbd.list());
            for (String imageName : imageList) {
                showImageDetails(imageName);
            }
            return imageList;
        } catch (RbdException e) {
            throw new RadosException("Failed to list RBD images.", e);
        }
    }

    @Override
    public void createImage(String imageName, long imageSize) throws RadosException {
        try {
            rbd.create(imageName, imageSize);
            showImageDetails(imageName);
        } catch (RbdException e) {
            throw new RadosException("Failed to create RBD image.", e);
        }
    }

    @Override
    public void resizeImage(String imageName, long newSize) throws RadosException {
        try {
            RbdImage image = rbd.open(imageName);
            image.resize(newSize);
            rbd.close(image);
        } catch (RbdException e) {
            throw new RadosException("Failed to resize RBD image.", e);
        }
    }

    @Override
    public void createSnapshot(String imageName, String snapName) throws RadosException {
        try {
            RbdImage image = rbd.open(imageName);
            image.snapCreate(snapName);
            image.snapProtect(snapName);
            rbd.close(image);
        } catch (RbdException e) {
            throw new RadosException("Failed to create snapshot.", e);
        }
    }

    @Override
    public void deleteSnapshot(String imageName, String snapName) throws RadosException {
        try {
            RbdImage image = rbd.open(imageName);
            image.snapUnprotect(snapName);
            image.snapRemove(snapName);
            rbd.close(image);
        } catch (RbdException e) {
            throw new RadosException("Failed to delete snapshot.", e);
        }
    }

    @Override
    public void cloneSnapshot(String parentImageName, String snapName, String newImageName) throws RadosException {
        try {
            rbd.clone(parentImageName, snapName, ioctx, newImageName, 0, 0);
        } catch (RbdException e) {
            throw new RadosException("Failed to clone snapshot.", e);
        }
    }

    @Override
    public void deleteImage(String imageName) throws RadosException {
        try {
            RbdImage image = rbd.open(imageName);
            rbd.remove(imageName);
            rbd.close(image);
        } catch (RbdException e) {
            throw new RadosException("Failed to delete RBD image.", e);
        }
    }

    private void showImageDetails(String imageName) throws RadosException {
        try {
            RbdImage image = rbd.open(imageName);
            RbdImageInfo info = image.stat();
            System.out.println("Image Name: " + imageName);
            System.out.println("Image Size: " + info.size);
            System.out.println("Order: " + info.order);
            rbd.close(image);
        } catch (RbdException e) {
            throw new RadosException("Failed to retrieve image details.", e);
        }
    }
}*/
