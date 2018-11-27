/**
 * Grill or hamburger button is shown on the DWP screen automatically
 * to hide the top menu when there are that many items included that they cannot be laid out on limited area.
 * Checks if Arrow action (such as back, up) is available in the DOM
 * Then clicks on a top action label
 *
 * * @param {object.name} options - Action name argument passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * Map<String, String> options = new HashMap<>();
 * options.put("arrow", "back");
 * boolean result = executeJavascriptTest("TrArrowAction", options);
 */
class TrCheckAndOpenTopMenu extends TestRunnerBase {

}