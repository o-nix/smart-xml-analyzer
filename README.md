# Smart XML Analyzer (Test Task)

## Intro

Imagine that you are writing a simple web crawler that locates a user-selected element on a web site with frequently changing information. You regularly face an issue that the crawler fails to find the element after minor page updates. After some analysis you decided to make your analyzer tolerant to minor website changes so that you don’t have to update the code every time.

## Requirements

Write a program that analyzes HTML and finds a specific element, even after changes, using a set of extracted attributes. 

## Nice to have

* Selector can be specified by using [Typesafe's config](https://github.com/lightbend/config) notation:
```bash
-Dselector=#make-everything-ok-button
```

* Logs (Logback) are at DEBUG level by default, so the algorithm shows some steps' data

## Expected Deliverables

* Binary version of the algorithm is under [build/libs](build/libs) folder (i.e. [dist.jar-1.0-SNAPSHOT-all.jar](build/libs/dist.jar-1.0-SNAPSHOT-all.jar))

* Binary can be built by using command `./gradlew shadowJar`

* Output for sample pages:
```bash
$ java -jar build/libs/dist.jar-1.0-SNAPSHOT-all.jar sample-files/origin.html sample-files/other-1.html
22:10:49.680 [main] DEBUG me.o_nix.agileengine.html_selectors.Application - Selector is: #make-everything-ok-button
22:10:49.683 [main] DEBUG me.o_nix.agileengine.html_selectors.Application - Original path is: sample-files/origin.html
22:10:49.683 [main] DEBUG me.o_nix.agileengine.html_selectors.Application - Other path is: sample-files/other-1.html
22:10:49.943 [main] DEBUG me.o_nix.agileengine.html_selectors.processing.Processor - Data gathered from initial document: HtmlOriginData(tagName=a, text=[Make, everything, OK], cssPath=#make-everything-ok-button, attributes={id=[make-everything-ok-button], class=[btn, btn-success], href=[#ok], title=[Make-Button], rel=[next], onclick=[javascript:window.okDone(), return false]})
22:10:50.432 [main] DEBUG me.o_nix.agileengine.html_selectors.processing.HtmlInspectionService - Possible element data: WeightAndData(weight=21310.0, data=HtmlOriginData(tagName=a, text=[Make, everything, OK], cssPath=#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > a.btn.btn-success, attributes={class=[btn, btn-success], href=[#check-and-ok], title=[Make-Button], rel=[done], onclick=[javascript:window.okDone(), return false]}))
#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > a.btn.btn-success
```

```bash
$ java -jar build/libs/dist.jar-1.0-SNAPSHOT-all.jar sample-files/origin.html sample-files/other-2.html
22:11:47.308 [main] DEBUG me.o_nix.agileengine.html_selectors.Application - Selector is: #make-everything-ok-button
22:11:47.310 [main] DEBUG me.o_nix.agileengine.html_selectors.Application - Original path is: sample-files/origin.html
22:11:47.311 [main] DEBUG me.o_nix.agileengine.html_selectors.Application - Other path is: sample-files/other-2.html
22:11:47.540 [main] DEBUG me.o_nix.agileengine.html_selectors.processing.Processor - Data gathered from initial document: HtmlOriginData(tagName=a, text=[Make, everything, OK], cssPath=#make-everything-ok-button, attributes={id=[make-everything-ok-button], class=[btn, btn-success], href=[#ok], title=[Make-Button], rel=[next], onclick=[javascript:window.okDone(), return false]})
22:11:48.025 [main] DEBUG me.o_nix.agileengine.html_selectors.processing.HtmlInspectionService - Possible element data: WeightAndData(weight=11410.0, data=HtmlOriginData(tagName=a, text=[Make, everything, OK], cssPath=#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > div.some-container > a.btn.test-link-ok, attributes={class=[btn, test-link-ok], href=[#ok], title=[Make-Button], rel=[next], onclick=[javascript:window.okComplete(), return false]}))
#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > div.some-container > a.btn.test-link-ok
```

```bash
$ java -jar build/libs/dist.jar-1.0-SNAPSHOT-all.jar sample-files/origin.html sample-files/other-3.html
22:12:12.450 [main] DEBUG me.o_nix.agileengine.html_selectors.Application - Selector is: #make-everything-ok-button
22:12:12.454 [main] DEBUG me.o_nix.agileengine.html_selectors.Application - Original path is: sample-files/origin.html
22:12:12.455 [main] DEBUG me.o_nix.agileengine.html_selectors.Application - Other path is: sample-files/other-3.html
22:12:12.685 [main] DEBUG me.o_nix.agileengine.html_selectors.processing.Processor - Data gathered from initial document: HtmlOriginData(tagName=a, text=[Make, everything, OK], cssPath=#make-everything-ok-button, attributes={id=[make-everything-ok-button], class=[btn, btn-success], href=[#ok], title=[Make-Button], rel=[next], onclick=[javascript:window.okDone(), return false]})
22:12:13.176 [main] DEBUG me.o_nix.agileengine.html_selectors.processing.HtmlInspectionService - Possible element data: WeightAndData(weight=21400.0, data=HtmlOriginData(tagName=a, text=[Do, anything, perfect], cssPath=#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-footer > a.btn.btn-success, attributes={class=[btn, btn-success], href=[#ok], title=[Do-Link], rel=[next], onclick=[javascript:window.okDone(), return false]}))
#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-footer > a.btn.btn-success
```

```bash
$ java -jar build/libs/dist.jar-1.0-SNAPSHOT-all.jar sample-files/origin.html sample-files/other-4.html
22:12:36.723 [main] DEBUG me.o_nix.agileengine.html_selectors.Application - Selector is: #make-everything-ok-button
22:12:36.727 [main] DEBUG me.o_nix.agileengine.html_selectors.Application - Original path is: sample-files/origin.html
22:12:36.727 [main] DEBUG me.o_nix.agileengine.html_selectors.Application - Other path is: sample-files/other-4.html
22:12:37.087 [main] DEBUG me.o_nix.agileengine.html_selectors.processing.Processor - Data gathered from initial document: HtmlOriginData(tagName=a, text=[Make, everything, OK], cssPath=#make-everything-ok-button, attributes={id=[make-everything-ok-button], class=[btn, btn-success], href=[#ok], title=[Make-Button], rel=[next], onclick=[javascript:window.okDone(), return false]})
22:12:37.569 [main] DEBUG me.o_nix.agileengine.html_selectors.processing.HtmlInspectionService - Possible element data: WeightAndData(weight=21400.0, data=HtmlOriginData(tagName=a, text=[Do, all, GREAT], cssPath=#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-footer > a.btn.btn-success, attributes={class=[btn, btn-success], href=[#ok], title=[Make-Button], rel=[next], onclick=[javascript:window.okFinalize(), return false]}))
#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-footer > a.btn.btn-success
```
