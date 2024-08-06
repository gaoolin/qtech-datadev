package com.qtech.ceph.rules;

import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.StorageClass;
import com.amazonaws.services.s3.model.Tag;
import com.amazonaws.services.s3.model.lifecycle.LifecycleFilter;
import com.amazonaws.services.s3.model.lifecycle.LifecyclePrefixPredicate;
import com.amazonaws.services.s3.model.lifecycle.LifecycleTagPredicate;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/01 10:04:33
 * desc   :  生命周期配置类
 */

public class LifecycleConfiguration {

    // Create a rule to archive objects with the "glacierobjects/" prefix to Glacier immediately.
    public static final BucketLifecycleConfiguration.Rule rule1 = new BucketLifecycleConfiguration.Rule()
            .withId("Archive immediately rule")
            .withFilter(new LifecycleFilter(new LifecyclePrefixPredicate("glacierobjects/")))
            .addTransition(new BucketLifecycleConfiguration.Transition().withDays(0).withStorageClass(StorageClass.Glacier))
            .withStatus(BucketLifecycleConfiguration.ENABLED);

    // Create a rule to transition objects to the Standard-Infrequent Access storage class
    // after 30 days, then to Glacier after 365 days. Amazon S3 will delete the objects after 3650 days.
    // The rule applies to all objects with the tag "archive" set to "true".
    public static final BucketLifecycleConfiguration.Rule rule2 = new BucketLifecycleConfiguration.Rule()
            .withId("Archive and then delete rule")
            .withFilter(new LifecycleFilter(new LifecycleTagPredicate(new Tag("archive", "true"))))
            .addTransition(new BucketLifecycleConfiguration.Transition().withDays(30).withStorageClass(StorageClass.StandardInfrequentAccess))
            .addTransition(new BucketLifecycleConfiguration.Transition().withDays(365).withStorageClass(StorageClass.Glacier))
            .withExpirationInDays(3650)
            .withStatus(BucketLifecycleConfiguration.ENABLED);

    public static final BucketLifecycleConfiguration.Rule rule3 = new BucketLifecycleConfiguration.Rule()
            .withId("Archive and then delete rule")
            .withExpirationInDays(70)
            .withStatus(BucketLifecycleConfiguration.ENABLED);

    public static final BucketLifecycleConfiguration.Rule rule4 = new BucketLifecycleConfiguration.Rule()
            .withId("Archive and then delete rule")
            .withExpirationInDays(1)
            .withStatus(BucketLifecycleConfiguration.ENABLED);
}
