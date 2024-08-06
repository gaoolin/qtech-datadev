package com.qtech.ceph.rbd.service;

/*
import com.ceph.rados.RadosException;
import com.ceph.rbd.jna.RbdImageInfo;
import com.ceph.rbd.jna.RbdSnapInfo;

import java.util.List;

*/
/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 11:10:14
 * desc   :
 *//*



*/
/**
 * Service interface for managing RBD (RADOS Block Device) images in Ceph.
 *//*

public interface RbdService {

    */
/**
     * Connects to the Ceph cluster.
     *
     * @throws RadosException if connection to Ceph fails
     *//*

    void connect() throws RadosException;

    */
/**
     * Lists all RBD images and their details.
     *
     * @return a list of image names
     * @throws RadosException if listing images fails
     *//*

    List<String> listImages() throws RadosException;

    */
/**
     * Creates an RBD image with the specified name and size.
     *
     * @param imageName the name of the image
     * @param imageSize the size of the image in bytes
     * @throws RadosException if creating the image fails
     *//*

    void createImage(String imageName, long imageSize) throws RadosException;

    */
/**
     * Resizes an existing RBD image.
     *
     * @param imageName the name of the image
     * @param newSize the new size of the image in bytes
     * @throws RadosException if resizing the image fails
     *//*

    void resizeImage(String imageName, long newSize) throws RadosException;

    */
/**
     * Creates a snapshot of an RBD image.
     *
     * @param imageName the name of the image
     * @param snapName the name of the snapshot
     * @throws RadosException if creating the snapshot fails
     *//*

    void createSnapshot(String imageName, String snapName) throws RadosException;

    */
/**
     * Deletes a snapshot of an RBD image.
     *
     * @param imageName the name of the image
     * @param snapName the name of the snapshot
     * @throws RadosException if deleting the snapshot fails
     *//*

    void deleteSnapshot(String imageName, String snapName) throws RadosException;

    */
/**
     * Clones a snapshot to create a new RBD image.
     *
     * @param parentImageName the name of the parent image
     * @param snapName the name of the snapshot
     * @param newImageName the name of the new image
     * @throws RadosException if cloning the snapshot fails
     *//*

    void cloneSnapshot(String parentImageName, String snapName, String newImageName) throws RadosException;

    */
/**
     * Deletes an RBD image.
     *
     * @param imageName the name of the image
     * @throws RadosException if deleting the image fails
     *//*

    void deleteImage(String imageName) throws RadosException;
}*/
