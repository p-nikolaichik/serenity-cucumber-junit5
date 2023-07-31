package org.openqa.selenium.remote.codec.w3c;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Kind;
import org.openqa.selenium.remote.codec.AbstractHttpCommandCodec;
import org.openqa.selenium.remote.internal.WebElementToJsonConverter;

public class W3CHttpCommandCodec extends AbstractHttpCommandCodec {
    private final PointerInput mouse;

    public W3CHttpCommandCodec() {
        this.mouse = new PointerInput(Kind.MOUSE, "mouse");
        String sessionId = "/session/:sessionId";
        this.alias("getElementAttribute", "executeScript");
        this.alias("getElementLocation", "getElementRect");
        this.alias("getElementLocationOnceScrolledIntoView", "executeScript");
        this.alias("getElementSize", "getElementRect");
        this.alias("isElementDisplayed", "executeScript");
        this.alias("submitElement", "executeScript");
        this.defineCommand("executeScript", post(sessionId + "/execute/sync"));
        this.defineCommand("executeAsyncScript", post(sessionId + "/execute/async"));
        this.alias("getPageSource", "executeScript");
        this.alias("clearLocalStorage", "executeScript");
        this.alias("getLocalStorageKeys", "executeScript");
        this.alias("setLocalStorageItem", "executeScript");
        this.alias("removeLocalStorageItem", "executeScript");
        this.alias("getLocalStorageItem", "executeScript");
        this.alias("getLocalStorageSize", "executeScript");
        this.alias("clearSessionStorage", "executeScript");
        this.alias("getSessionStorageKey", "executeScript");
        this.alias("setSessionStorageItem", "executeScript");
        this.alias("removeSessionStorageItem", "executeScript");
        this.alias("getSessionStorageItem", "executeScript");
        this.alias("getSessionStorageSize", "executeScript");
        String window = sessionId + "/window";
        this.defineCommand("maximizeCurrentWindow", post(window + "/maximize"));
        this.defineCommand("minimizeCurrentWindow", post(window + "/minimize"));
        this.defineCommand("getCurrentWindowSize", get(window + "/rect"));
        this.defineCommand("setCurrentWindowSize", post(window + "/rect"));
        this.alias("getWindowPosition", "getCurrentWindowSize");
        this.alias("setWindowPosition", "setCurrentWindowSize");
        this.defineCommand("getCurrentWindowHandle", get(window));
        this.defineCommand("getWindowHandles", get(window + "/handles"));
        String alert = sessionId + "/alert";
        this.defineCommand("acceptAlert", post(alert + "/accept"));
        this.defineCommand("dismissAlert", post(alert + "/dismiss"));
        this.defineCommand("getAlertText", get(alert + "/text"));
        this.defineCommand("setAlertValue", post(alert + "/text"));
        this.defineCommand("printPage", post(sessionId + "/print"));
        // TODO: temporarily changed the /se/file endpoint to the old /file endpoint as the chromedriver doesn't currently support the /se/file endpoint.
        // Needs to be removed when this issue is fixed by chrome driver
        this.defineCommand("uploadFile", post(sessionId + "/file"));
        this.defineCommand("getActiveElement", get(sessionId + "/element/active"));
        this.defineCommand("actions", post(sessionId + "/actions"));
        this.defineCommand("clearActionState", delete(sessionId + "/actions"));
        String elementId = sessionId + "/element/:id";
        this.defineCommand("getElementDomProperty", get(elementId + "/property/:name"));
        this.defineCommand("getElementDomAttribute", get(elementId + "/attribute/:name"));
        this.defineCommand("getElementAriaRole", get(elementId + "/computedrole"));
        this.defineCommand("getElementAccessibleName", get(elementId + "/computedlabel"));
        this.defineCommand("getElementShadowRoot", get(elementId + "/shadow"));
        this.defineCommand("findElementFromShadowRoot", post(sessionId + "/shadow/:shadowId/element"));
        this.defineCommand("findElementsFromShadowRoot", post(sessionId + "/shadow/:shadowId/elements"));
        this.defineCommand("getLog", post(sessionId + "/se/log"));
        this.defineCommand("getAvailableLogTypes", get(sessionId + "/se/log/types"));
    }

    protected Map<String, ?> amendParameters(String name, Map<String, ?> parameters) {
        byte var4 = -1;
        switch(name.hashCode()) {
            case -2096670030:
                if (name.equals("clearSessionStorage")) {
                    var4 = 13;
                }
                break;
            case -2026985042:
                if (name.equals("getSessionStorageItem")) {
                    var4 = 17;
                }
                break;
            case -2026697060:
                if (name.equals("getSessionStorageSize")) {
                    var4 = 18;
                }
                break;
            case -1911313468:
                if (name.equals("submitElement")) {
                    var4 = 23;
                }
                break;
            case -1907320969:
                if (name.equals("setAlertValue")) {
                    var4 = 21;
                }
                break;
            case -1813372083:
                if (name.equals("getElementLocationOnceScrolledIntoView")) {
                    var4 = 5;
                }
                break;
            case -1494286267:
                if (name.equals("setLocalStorageItem")) {
                    var4 = 9;
                }
                break;
            case -955349351:
                if (name.equals("findChildElement")) {
                    var4 = 0;
                }
                break;
            case -913851963:
                if (name.equals("sendKeysToElement")) {
                    var4 = 20;
                }
                break;
            case -826831101:
                if (name.equals("findElement")) {
                    var4 = 2;
                }
                break;
            case -565126275:
                if (name.equals("clearLocalStorage")) {
                    var4 = 7;
                }
                break;
            case 49441593:
                if (name.equals("getLocalStorageItem")) {
                    var4 = 11;
                }
                break;
            case 49487386:
                if (name.equals("getLocalStorageKeys")) {
                    var4 = 8;
                }
                break;
            case 49729575:
                if (name.equals("getLocalStorageSize")) {
                    var4 = 12;
                }
                break;
            case 99443548:
                if (name.equals("removeSessionStorageItem")) {
                    var4 = 16;
                }
                break;
            case 138039760:
                if (name.equals("findElements")) {
                    var4 = 3;
                }
                break;
            case 448941306:
                if (name.equals("findChildElements")) {
                    var4 = 1;
                }
                break;
            case 509225914:
                if (name.equals("setSessionStorageItem")) {
                    var4 = 15;
                }
                break;
            case 765898852:
                if (name.equals("getSessionStorageKey")) {
                    var4 = 14;
                }
                break;
            case 984876928:
                if (name.equals("getPageSource")) {
                    var4 = 6;
                }
                break;
            case 1137686631:
                if (name.equals("removeLocalStorageItem")) {
                    var4 = 10;
                }
                break;
            case 1659754143:
                if (name.equals("setTimeout")) {
                    var4 = 22;
                }
                break;
            case 1713122934:
                if (name.equals("getElementAttribute")) {
                    var4 = 4;
                }
                break;
            case 1982256783:
                if (name.equals("isElementDisplayed")) {
                    var4 = 19;
                }
        }

        switch(var4) {
            case 0:
            case 1:
            case 2:
            case 3:
                String using = (String)parameters.get("using");
                Object value = parameters.get("value");
                if (value instanceof String) {
                    String stringValue = (String)value;
                    byte var13 = -1;
                    switch(using.hashCode()) {
                        case -348656589:
                            if (using.equals("class name")) {
                                var13 = 0;
                            }
                            break;
                        case 3355:
                            if (using.equals("id")) {
                                var13 = 1;
                            }
                            break;
                        case 3373707:
                            if (using.equals("name")) {
                                var13 = 2;
                            }
                    }

                    switch(var13) {
                        case 0:
                            if (stringValue.matches(".*\\s.*")) {
                                throw new InvalidSelectorException("Compound class names not permitted");
                            }

                            return this.amendLocatorToCssSelector(parameters, "." + this.cssEscape(stringValue));
                        case 1:
                            return this.amendLocatorToCssSelector(parameters, "#" + this.cssEscape(stringValue));
                        case 2:
                            return this.amendLocatorToCssSelector(parameters, "*[name='" + stringValue + "']");
                    }
                }

                return parameters;
            case 4:
                return this.executeAtom("getAttribute.js", this.asElement(parameters.get("id")), parameters.get("name"));
            case 5:
                return this.toScript("var e = arguments[0]; e.scrollIntoView({behavior: 'instant', block: 'end', inline: 'nearest'}); var rect = e.getBoundingClientRect(); return {'x': rect.left, 'y': rect.top};", this.asElement(parameters.get("id")));
            case 6:
                return this.toScript("var source = document.documentElement.outerHTML; \nif (!source) { source = new XMLSerializer().serializeToString(document); }\nreturn source;");
            case 7:
                return this.toScript("localStorage.clear()");
            case 8:
                return this.toScript("return Object.keys(localStorage)");
            case 9:
                return this.toScript("localStorage.setItem(arguments[0], arguments[1])", parameters.get("key"), parameters.get("value"));
            case 10:
                return this.toScript("var item = localStorage.getItem(arguments[0]); localStorage.removeItem(arguments[0]); return item", parameters.get("key"));
            case 11:
                return this.toScript("return localStorage.getItem(arguments[0])", parameters.get("key"));
            case 12:
                return this.toScript("return localStorage.length");
            case 13:
                return this.toScript("sessionStorage.clear()");
            case 14:
                return this.toScript("return Object.keys(sessionStorage)");
            case 15:
                return this.toScript("sessionStorage.setItem(arguments[0], arguments[1])", parameters.get("key"), parameters.get("value"));
            case 16:
                return this.toScript("var item = sessionStorage.getItem(arguments[0]); sessionStorage.removeItem(arguments[0]); return item", parameters.get("key"));
            case 17:
                return this.toScript("return sessionStorage.getItem(arguments[0])", parameters.get("key"));
            case 18:
                return this.toScript("return sessionStorage.length");
            case 19:
                return this.executeAtom("isDisplayed.js", this.asElement(parameters.get("id")));
            case 20:
                Object rawValue = parameters.get("value");
                Stream source;
                if (rawValue instanceof Collection) {
                    source = ((Collection)rawValue).stream();
                } else {
                    source = Stream.of((CharSequence[])rawValue);
                }

                String text = (String)source.collect(Collectors.joining());
                return (Map) ImmutableMap.builder().putAll((Map)parameters.entrySet().stream().filter((e) -> {
                    return !"text".equals(e.getKey());
                }).filter((e) -> {
                    return !"value".equals(e.getKey());
                }).collect(Collectors.toMap(Entry::getKey, Entry::getValue))).put("text", text).put("value", this.stringToUtf8Array(text)).build();
            case 21:
                return (Map) ImmutableMap.builder().put("text", parameters.get("text")).put("value", this.stringToUtf8Array((String)parameters.get("text"))).build();
            case 22:
                String timeoutType = (String)parameters.get("type");
                Number duration = (Number)parameters.get("ms");
                if (timeoutType == null) {
                    return parameters;
                }

                return (Map) ImmutableMap.builder().putAll((Map)parameters.entrySet().stream().filter((e) -> {
                    return !timeoutType.equals(e.getKey());
                }).collect(Collectors.toMap(Entry::getKey, Entry::getValue))).put(timeoutType, duration).build();
            case 23:
                return this.toScript("var form = arguments[0];\nwhile (form.nodeName != \"FORM\" && form.parentNode) {\n  form = form.parentNode;\n}\nif (!form) { throw Error('Unable to find containing form element'); }\nif (!form.ownerDocument) { throw Error('Unable to find owning document'); }\nvar e = form.ownerDocument.createEvent('Event');\ne.initEvent('submit', true, true);\nif (form.dispatchEvent(e)) { HTMLFormElement.prototype.submit.call(form) }\n", this.asElement(parameters.get("id")));
            default:
                return parameters;
        }
    }

    private List<String> stringToUtf8Array(String toConvert) {
        List<String> toReturn = new ArrayList();

        int next;
        for(int offset = 0; offset < toConvert.length(); offset += Character.charCount(next)) {
            next = toConvert.codePointAt(offset);
            toReturn.add((new StringBuilder()).appendCodePoint(next).toString());
        }

        return toReturn;
    }

    private Map<String, ?> executeAtom(String atomFileName, Object... args) {
        try {
            String scriptName = "/org/openqa/selenium/remote/" + atomFileName;
            URL url = this.getClass().getResource(scriptName);
            String rawFunction = Resources.toString(url, StandardCharsets.UTF_8);
            String script = String.format("return (%s).apply(null, arguments);", rawFunction);
            return this.toScript(script, args);
        } catch (NullPointerException | IOException var7) {
            throw new WebDriverException(var7);
        }
    }

    private Map<String, ?> toScript(String script, Object... args) {
        List<Object> convertedArgs = (List)Stream.of(args).map(new WebElementToJsonConverter()).collect(Collectors.toList());
        return ImmutableMap.of("script", script, "args", convertedArgs);
    }

    private Map<String, String> asElement(Object id) {
        return ImmutableMap.of("element-6066-11e4-a52e-4f735466cecf", (String)id);
    }

    private String cssEscape(String using) {
        using = using.replaceAll("([\\s'\"\\\\#.:;,!?+<>=~*^$|%&@`{}\\-\\/\\[\\]\\(\\)])", "\\\\$1");
        if (using.length() > 0 && Character.isDigit(using.charAt(0))) {
            using = "\\" + (30 + Integer.parseInt(using.substring(0, 1))) + " " + using.substring(1);
        }

        return using;
    }

    private Map<String, ?> amendLocatorToCssSelector(Map<String, ?> parameters, String value) {
        Map<String, Object> amended = new HashMap(parameters);
        amended.put("using", "css selector");
        amended.put("value", value);
        return amended;
    }
}