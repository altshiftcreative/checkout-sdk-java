"use strict";(self.webpackChunkcheckout_sdk_java_docs=self.webpackChunkcheckout_sdk_java_docs||[]).push([[325],{3905:function(e,t,n){n.d(t,{Zo:function(){return l},kt:function(){return m}});var r=n(7294);function a(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function o(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function i(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?o(Object(n),!0).forEach((function(t){a(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):o(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function c(e,t){if(null==e)return{};var n,r,a=function(e,t){if(null==e)return{};var n,r,a={},o=Object.keys(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var s=r.createContext({}),p=function(e){var t=r.useContext(s),n=t;return e&&(n="function"==typeof e?e(t):i(i({},t),e)),n},l=function(e){var t=p(e.components);return r.createElement(s.Provider,{value:t},e.children)},d={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},u=r.forwardRef((function(e,t){var n=e.components,a=e.mdxType,o=e.originalType,s=e.parentName,l=c(e,["components","mdxType","originalType","parentName"]),u=p(n),m=a,v=u["".concat(s,".").concat(m)]||u[m]||d[m]||o;return n?r.createElement(v,i(i({ref:t},l),{},{components:n})):r.createElement(v,i({ref:t},l))}));function m(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var o=n.length,i=new Array(o);i[0]=u;var c={};for(var s in t)hasOwnProperty.call(t,s)&&(c[s]=t[s]);c.originalType=e,c.mdxType="string"==typeof e?e:a,i[1]=c;for(var p=2;p<o;p++)i[p]=n[p];return r.createElement.apply(null,i)}return r.createElement.apply(null,n)}u.displayName="MDXCreateElement"},6803:function(e,t,n){n.r(t),n.d(t,{frontMatter:function(){return c},contentTitle:function(){return s},metadata:function(){return p},toc:function(){return l},default:function(){return u}});var r=n(7462),a=n(3366),o=(n(7294),n(3905)),i=["components"],c={id:"environment",title:"Environment",sidebar_position:3},s=void 0,p={unversionedId:"getting-started/environment",id:"getting-started/environment",isDocsHomePage:!1,title:"Environment",description:"The SDK will target an environment based on the useSandbox value you provide when you initialize it:",source:"@site/docs/getting-started/environment.md",sourceDirName:"getting-started",slug:"/getting-started/environment",permalink:"/checkout-sdk-java/docs/getting-started/environment",version:"current",sidebarPosition:3,frontMatter:{id:"environment",title:"Environment",sidebar_position:3},sidebar:"tutorialSidebar",previous:{title:"Initialize",permalink:"/checkout-sdk-java/docs/getting-started/initialize"},next:{title:"Error Handling",permalink:"/checkout-sdk-java/docs/getting-started/errors"}},l=[{value:"Testing",id:"testing",children:[]}],d={toc:l};function u(e){var t=e.components,n=(0,a.Z)(e,i);return(0,o.kt)("wrapper",(0,r.Z)({},d,n,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("p",null,"The SDK will target an environment based on the ",(0,o.kt)("strong",{parentName:"p"},"useSandbox")," value you provide when you initialize it:"),(0,o.kt)("p",null,(0,o.kt)("strong",{parentName:"p"},"Sandbox")," (",(0,o.kt)("em",{parentName:"p"},"true"),"): ",(0,o.kt)("a",{parentName:"p",href:"https://api.sandbox.checkout.com"},"https://api.sandbox.checkout.com")),(0,o.kt)("p",null,(0,o.kt)("strong",{parentName:"p"},"Live")," (",(0,o.kt)("em",{parentName:"p"},"false"),"): ",(0,o.kt)("a",{parentName:"p",href:"https://api.checkout.com"},"https://api.checkout.com")),(0,o.kt)("div",{className:"admonition admonition-caution alert alert--warning"},(0,o.kt)("div",{parentName:"div",className:"admonition-heading"},(0,o.kt)("h5",{parentName:"div"},(0,o.kt)("span",{parentName:"h5",className:"admonition-icon"},(0,o.kt)("svg",{parentName:"span",xmlns:"http://www.w3.org/2000/svg",width:"16",height:"16",viewBox:"0 0 16 16"},(0,o.kt)("path",{parentName:"svg",fillRule:"evenodd",d:"M8.893 1.5c-.183-.31-.52-.5-.887-.5s-.703.19-.886.5L.138 13.499a.98.98 0 0 0 0 1.001c.193.31.53.501.886.501h13.964c.367 0 .704-.19.877-.5a1.03 1.03 0 0 0 .01-1.002L8.893 1.5zm.133 11.497H6.987v-2.003h2.039v2.003zm0-3.004H6.987V5.987h2.039v4.006z"}))),"caution")),(0,o.kt)("div",{parentName:"div",className:"admonition-content"},(0,o.kt)("p",{parentName:"div"},'Public and secret key pairs will only work for their relevant environment. Sandbox keys begin with "pk',"_","test","_",'" or "sk',"_","test","_",'". You will receive separate Live keys for use in your production environment when going live with Checkout.com.'))),(0,o.kt)("h2",{id:"testing"},"Testing"),(0,o.kt)("p",null,"If you are testing in the Sandbox environment, Checkout.com provides a list of ",(0,o.kt)("a",{parentName:"p",href:"https://docs.checkout.com/testing/test-card-numbers"},"test card details"),". You can also simulate a lot of edge cases like Declines, by using ",(0,o.kt)("a",{parentName:"p",href:"https://docs.checkout.com/testing/response-code-testing"},"special transaction values"),"."),(0,o.kt)("div",{className:"admonition admonition-note alert alert--secondary"},(0,o.kt)("div",{parentName:"div",className:"admonition-heading"},(0,o.kt)("h5",{parentName:"div"},(0,o.kt)("span",{parentName:"h5",className:"admonition-icon"},(0,o.kt)("svg",{parentName:"span",xmlns:"http://www.w3.org/2000/svg",width:"14",height:"16",viewBox:"0 0 14 16"},(0,o.kt)("path",{parentName:"svg",fillRule:"evenodd",d:"M6.3 5.69a.942.942 0 0 1-.28-.7c0-.28.09-.52.28-.7.19-.18.42-.28.7-.28.28 0 .52.09.7.28.18.19.28.42.28.7 0 .28-.09.52-.28.7a1 1 0 0 1-.7.3c-.28 0-.52-.11-.7-.3zM8 7.99c-.02-.25-.11-.48-.31-.69-.2-.19-.42-.3-.69-.31H6c-.27.02-.48.13-.69.31-.2.2-.3.44-.31.69h1v3c.02.27.11.5.31.69.2.2.42.31.69.31h1c.27 0 .48-.11.69-.31.2-.19.3-.42.31-.69H8V7.98v.01zM7 2.3c-3.14 0-5.7 2.54-5.7 5.68 0 3.14 2.56 5.7 5.7 5.7s5.7-2.55 5.7-5.7c0-3.15-2.56-5.69-5.7-5.69v.01zM7 .98c3.86 0 7 3.14 7 7s-3.14 7-7 7-7-3.12-7-7 3.14-7 7-7z"}))),"note")),(0,o.kt)("div",{parentName:"div",className:"admonition-content"},(0,o.kt)("p",{parentName:"div"},"You can also test 3D Secure with the test cards provided. If you want to test 3DS 2 flows, use the cards mentioned ",(0,o.kt)("a",{parentName:"p",href:"https://docs.checkout.com/testing/3d-secure-testing"},"here"),"."))))}u.isMDXComponent=!0}}]);