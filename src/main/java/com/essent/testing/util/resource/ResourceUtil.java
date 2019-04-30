package com.essent.testing.util.resource;

import com.essent.testing.database.OsUtils;

import java.net.URL;

public class ResourceUtil {
  private static final String resourceFolder;

  static {
    // First find a defined resource on the resource path, this is usually
    // somewhere in the /target/test-classes/ directory.
    URL fileName = ResourceUtil.class.getResource("/common.properties");
    if (fileName != null) {
      // Get the full path, take OS quircks into account.
      String fullPath = OsUtils.getFileLocation(fileName);
      // Substract the target path
      String path = fullPath.substring(0, fullPath.indexOf("/target/test-classes/"));
      // And add the resources folder again, we now have the full path to the root
      // of the resources folder.
      resourceFolder = path + "/src/test/resources";
    } else {
      // No can do, tests will fail.
      resourceFolder = "Resource folder not found";
    }
  }

  /**
   * Get the full path for the given resource. The resource is expected to be in the
   * src/test/resource folder
   *
   * @param resource name of the resource.
   * @return the full path to the original resource on the filesystem.
   */
  public static String toPath(String resource) {
    return resourceFolder + resource;
  }
}
