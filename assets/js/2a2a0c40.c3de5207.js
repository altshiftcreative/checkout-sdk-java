"use strict";(self.webpackChunkcheckout_sdk_java_docs=self.webpackChunkcheckout_sdk_java_docs||[]).push([[697],{3905:function(e,t,n){n.d(t,{Zo:function(){return s},kt:function(){return m}});var r=n(7294);function a(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function o(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function i(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?o(Object(n),!0).forEach((function(t){a(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):o(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function c(e,t){if(null==e)return{};var n,r,a=function(e,t){if(null==e)return{};var n,r,a={},o=Object.keys(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var l=r.createContext({}),u=function(e){var t=r.useContext(l),n=t;return e&&(n="function"==typeof e?e(t):i(i({},t),e)),n},s=function(e){var t=u(e.components);return r.createElement(l.Provider,{value:t},e.children)},p={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},d=r.forwardRef((function(e,t){var n=e.components,a=e.mdxType,o=e.originalType,l=e.parentName,s=c(e,["components","mdxType","originalType","parentName"]),d=u(n),m=a,f=d["".concat(l,".").concat(m)]||d[m]||p[m]||o;return n?r.createElement(f,i(i({ref:t},s),{},{components:n})):r.createElement(f,i({ref:t},s))}));function m(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var o=n.length,i=new Array(o);i[0]=d;var c={};for(var l in t)hasOwnProperty.call(t,l)&&(c[l]=t[l]);c.originalType=e,c.mdxType="string"==typeof e?e:a,i[1]=c;for(var u=2;u<o;u++)i[u]=n[u];return r.createElement.apply(null,i)}return r.createElement.apply(null,n)}d.displayName="MDXCreateElement"},5238:function(e,t,n){n.r(t),n.d(t,{frontMatter:function(){return c},contentTitle:function(){return l},metadata:function(){return u},toc:function(){return s},default:function(){return d}});var r=n(7462),a=n(3366),o=(n(7294),n(3905)),i=["components"],c={id:"install",title:"Install",sidebar_position:1},l=void 0,u={unversionedId:"getting-started/install",id:"getting-started/install",isDocsHomePage:!1,title:"Install",description:"The jar, javadoc, and sources are all available from Maven Central. You can import the library into your project like so:",source:"@site/docs/getting-started/install.md",sourceDirName:"getting-started",slug:"/getting-started/install",permalink:"/checkout-sdk-java/docs/getting-started/install",version:"current",sidebarPosition:1,frontMatter:{id:"install",title:"Install",sidebar_position:1},sidebar:"tutorialSidebar",previous:{title:"Introduction",permalink:"/checkout-sdk-java/docs/introduction"},next:{title:"Initialize",permalink:"/checkout-sdk-java/docs/getting-started/initialize"}},s=[{value:"Gradle",id:"gradle",children:[]},{value:"Maven",id:"maven",children:[]},{value:"Import",id:"import",children:[]}],p={toc:s};function d(e){var t=e.components,n=(0,a.Z)(e,i);return(0,o.kt)("wrapper",(0,r.Z)({},p,n,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("p",null,"The jar, javadoc, and sources are all available from ",(0,o.kt)("a",{parentName:"p",href:"https://search.maven.org/artifact/com.checkout/checkout-sdk-java"},"Maven Central"),". You can import the library into your project like so:"),(0,o.kt)("h3",{id:"gradle"},"Gradle"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-groovy"},"dependencies {\n    implementation 'com.checkout:checkout-sdk-java:2.3.1'\n}\n")),(0,o.kt)("h3",{id:"maven"},"Maven"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-xml"},"<dependency>\n    <groupId>com.checkout</groupId>\n    <artifactId>checkout-sdk-java</artifactId>\n    <version>2.3.1</version>\n</dependency>\n")),(0,o.kt)("h3",{id:"import"},"Import"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-java"},"import com.checkout.CheckoutApi;\nimport com.checkout.CheckoutApiImpl;\n")))}d.isMDXComponent=!0}}]);