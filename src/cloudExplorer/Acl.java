/**
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cloudExplorer;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.Permission;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class Acl {

    NewJFrame mainFrame;

    void setAccess(String id, int what, String access_key, String secret_key, String endpoint, String bucket) {
        try {

            Collection<Grant> grantCollection = new ArrayList<Grant>();
            AWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
            AmazonS3 s3Client = new AmazonS3Client(credentials,
                    new ClientConfiguration());
            if (endpoint.contains("amazonaws.com")) {
                String aws_endpoint = s3Client.getBucketLocation(new GetBucketLocationRequest(bucket));
                if (aws_endpoint.contains("US")) {
                    s3Client.setEndpoint("https://s3.amazonaws.com");
                } else if (aws_endpoint.contains("us-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("eu-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("ap-")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("sa-east-1")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else {
                    s3Client.setEndpoint("https://s3." + aws_endpoint + ".amazonaws.com");
                }
            } else {
                s3Client.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
                s3Client.setEndpoint(endpoint);
            }

            AccessControlList bucketAcl = s3Client.getBucketAcl(bucket);
            Grant grant = null;
            if (what == 0) {

                grant = new Grant(
                        new CanonicalGrantee(id),
                        Permission.Read);
                grantCollection.add(grant);
            }

            if (what == 1) {
                grant = new Grant(
                        new CanonicalGrantee(id),
                        Permission.FullControl);
                grantCollection.add(grant);
            }

            if (what == 3) {
                bucketAcl.getGrants().clear();
            }

            bucketAcl.getGrants().addAll(grantCollection);
            s3Client.setBucketAcl(bucket, bucketAcl);

        } catch (AmazonServiceException ase) {
            NewJFrame.jTextArea1.append("\n\nError: " + ase.getErrorMessage());
        }
    }

    void setACLpublic(String object, String access_key, String secret_key, String endpoint, String bucket) {
        try {
            AWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
            AmazonS3 s3Client = new AmazonS3Client(credentials,
                    new ClientConfiguration());
            if (endpoint.contains("amazonaws.com")) {
                String aws_endpoint = s3Client.getBucketLocation(new GetBucketLocationRequest(bucket));
                if (aws_endpoint.contains("US")) {
                    s3Client.setEndpoint("https://s3.amazonaws.com");
                } else if (aws_endpoint.contains("us-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("eu-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("ap-")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("sa-east-1")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else {
                    s3Client.setEndpoint("https://s3." + aws_endpoint + ".amazonaws.com");
                }
            } else {
                s3Client.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
                s3Client.setEndpoint(endpoint);
            }
            s3Client.setObjectAcl(bucket, object, CannedAccessControlList.PublicRead);
        } catch (Exception setACLpublic) {
            mainFrame.jTextArea1.append("\nException occurred in ACL");
        }
    }

    void setACLprivate(String object, String access_key, String secret_key, String endpoint, String bucket) {
        try {
            AWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
            AmazonS3 s3Client = new AmazonS3Client(credentials,
                    new ClientConfiguration());
            if (endpoint.contains("amazonaws.com")) {
                String aws_endpoint = s3Client.getBucketLocation(new GetBucketLocationRequest(bucket));
                if (aws_endpoint.contains("US")) {
                    s3Client.setEndpoint("https://s3.amazonaws.com");
                } else if (aws_endpoint.contains("us-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("eu-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("ap-")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("sa-east-1")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else {
                    s3Client.setEndpoint("https://s3." + aws_endpoint + ".amazonaws.com");
                }
            } else {
                s3Client.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
                s3Client.setEndpoint(endpoint);
            }
            s3Client.setObjectAcl(bucket, object, CannedAccessControlList.Private);
        } catch (Exception setACLprivate) {
            mainFrame.jTextArea1.append("\nException occurred in setACLprivate");
        }
    }

    String viewACL(String object, String access_key, String secret_key, String endpoint, String bucket) {
        String message = null;
        try {
            AWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
            AmazonS3 s3Client = new AmazonS3Client(credentials,
                    new ClientConfiguration());

            if (endpoint.contains("amazonaws.com")) {
                String aws_endpoint = s3Client.getBucketLocation(new GetBucketLocationRequest(bucket));
                if (aws_endpoint.contains("US")) {
                    s3Client.setEndpoint("https://s3.amazonaws.com");
                } else if (aws_endpoint.contains("us-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("eu-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("ap-")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("sa-east-1")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else {
                    s3Client.setEndpoint("https://s3." + aws_endpoint + ".amazonaws.com");
                }
            } else {
                s3Client.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
                s3Client.setEndpoint(endpoint);
            }
            message = s3Client.getObjectAcl(bucket, object).toString();
        } catch (Exception viewACL) {
            mainFrame.jTextArea1.append("\nException occurred in viewACL");
        }

        return object + ":     " + message;
    }

    void setBUCKETwebsite(String object, String access_key, String secret_key, String endpoint, String bucket) {
        try {
            AWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
            AmazonS3 s3Client = new AmazonS3Client(credentials,
                    new ClientConfiguration());
            if (endpoint.contains("amazonaws.com")) {
                String aws_endpoint = s3Client.getBucketLocation(new GetBucketLocationRequest(bucket));
                if (aws_endpoint.contains("US")) {
                    s3Client.setEndpoint("https://s3.amazonaws.com");
                } else if (aws_endpoint.contains("us-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("eu-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("ap-")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("sa-east-1")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else {
                    s3Client.setEndpoint("https://s3." + aws_endpoint + ".amazonaws.com");
                }
            } else {
                s3Client.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
                s3Client.setEndpoint(endpoint);
            }
            BucketWebsiteConfiguration bucketWebsiteConfiguration = s3Client.getBucketWebsiteConfiguration(bucket);
            s3Client.setBucketAcl(bucket, CannedAccessControlList.PublicRead);
            s3Client.setBucketWebsiteConfiguration(bucket, new BucketWebsiteConfiguration("index.html", "error.html"));
        } catch (Exception setACLpublic) {
            mainFrame.jTextArea1.append("\nException occurred in ACL");
        }
    }

    void removeBUCKETwebsite(String object, String access_key, String secret_key, String endpoint, String bucket) {
        try {
            AWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
            AmazonS3 s3Client = new AmazonS3Client(credentials,
                    new ClientConfiguration());
            if (endpoint.contains("amazonaws.com")) {
                String aws_endpoint = s3Client.getBucketLocation(new GetBucketLocationRequest(bucket));
                if (aws_endpoint.contains("US")) {
                    s3Client.setEndpoint("https://s3.amazonaws.com");
                } else if (aws_endpoint.contains("us-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("eu-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("ap-")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("sa-east-1")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else {
                    s3Client.setEndpoint("https://s3." + aws_endpoint + ".amazonaws.com");
                }
            } else {
                s3Client.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
                s3Client.setEndpoint(endpoint);
            }
            s3Client.deleteBucketWebsiteConfiguration(bucket);
        } catch (Exception removeBUCKETwebsite) {
            mainFrame.jTextArea1.append("\nException occurred in ACL");
        }
    }

    String setACLurl(String object, String access_key, String secret_key, String endpoint, String bucket) {
        String URL = null;
        try {
            AWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
            AmazonS3 s3Client = new AmazonS3Client(credentials,
                    new ClientConfiguration());
             if (endpoint.contains("amazonaws.com")) {
                String aws_endpoint = s3Client.getBucketLocation(new GetBucketLocationRequest(bucket));
                if (aws_endpoint.contains("US")) {
                    s3Client.setEndpoint("https://s3.amazonaws.com");
                } else if (aws_endpoint.contains("us-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("eu-west")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("ap-")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else if (aws_endpoint.contains("sa-east-1")) {
                    s3Client.setEndpoint("https://s3-" + aws_endpoint + ".amazonaws.com");
                } else {
                    s3Client.setEndpoint("https://s3." + aws_endpoint + ".amazonaws.com");
                }
            } else {
                s3Client.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
                s3Client.setEndpoint(endpoint);
            }
            java.util.Date expiration = new java.util.Date();
            long milliSeconds = expiration.getTime();
            milliSeconds += 1000 * 60 * 1000; // Add 1 hour.
            expiration.setTime(milliSeconds);
            GeneratePresignedUrlRequest generatePresignedUrlRequest
                    = new GeneratePresignedUrlRequest(bucket, object);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            generatePresignedUrlRequest.setExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            URL = ("Pre-Signed URL = " + url.toString());
            StringSelection stringSelection = new StringSelection(url.toString());
        } catch (Exception setACLpublic) {
        }
        return URL;
    }
}
