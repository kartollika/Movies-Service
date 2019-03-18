!function (e, t) {
    "object" == typeof exports && "object" == typeof module ? module.exports = t(require("swiper/dist/js/swiper.js")) : "function" == typeof define && define.amd ? define("VueAwesomeSwiper", ["swiper"], t) : "object" == typeof exports ? exports.VueAwesomeSwiper = t(require("swiper/dist/js/swiper.js")) : e.VueAwesomeSwiper = t(e.Swiper)
}(this, function (e) {
    return function (e) {
        function t(i) {
            if (n[i]) return n[i].exports;
            var s = n[i] = {i: i, l: !1, exports: {}};
            return e[i].call(s.exports, s, s.exports, t), s.l = !0, s.exports
        }

        var n = {};
        return t.m = e, t.c = n, t.i = function (e) {
            return e
        }, t.d = function (e, n, i) {
            t.o(e, n) || Object.defineProperty(e, n, {configurable: !1, enumerable: !0, get: i})
        }, t.n = function (e) {
            var n = e && e.__esModule ? function () {
                return e.default
            } : function () {
                return e
            };
            return t.d(n, "a", n), n
        }, t.o = function (e, t) {
            return Object.prototype.hasOwnProperty.call(e, t)
        }, t.p = "/", t(t.s = 4)
    }([function (t, n) {
        t.exports = e
    }, function (e, t) {
        e.exports = function (e, t, n, i, s, r) {
            var o, a = e = e || {}, u = typeof e.default;
            "object" !== u && "function" !== u || (o = e, a = e.default);
            var p = "function" == typeof a ? a.options : a;
            t && (p.render = t.render, p.staticRenderFns = t.staticRenderFns, p._compiled = !0), n && (p.functional = !0), s && (p._scopeId = s);
            var l;
            if (r ? (l = function (e) {
                e = e || this.$vnode && this.$vnode.ssrContext || this.parent && this.parent.$vnode && this.parent.$vnode.ssrContext, e || "undefined" == typeof __VUE_SSR_CONTEXT__ || (e = __VUE_SSR_CONTEXT__), i && i.call(this, e), e && e._registeredComponents && e._registeredComponents.add(r)
            }, p._ssrRegister = l) : i && (l = i), l) {
                var c = p.functional, d = c ? p.render : p.beforeCreate;
                c ? (p._injectStyles = l, p.render = function (e, t) {
                    return l.call(t), d(e, t)
                }) : p.beforeCreate = d ? [].concat(d, l) : [l]
            }
            return {esModule: o, exports: a, options: p}
        }
    }, function (e, t, n) {
        "use strict";
        Object.defineProperty(t, "__esModule", {value: !0});
        var i = n(5), s = n.n(i), r = n(8), o = n(1), a = o(s.a, r.a, !1, null, null, null);
        t.default = a.exports
    }, function (e, t, n) {
        "use strict";
        Object.defineProperty(t, "__esModule", {value: !0});
        var i = n(6), s = n.n(i), r = n(7), o = n(1), a = o(s.a, r.a, !1, null, null, null);
        t.default = a.exports
    }, function (e, t, n) {
        "use strict";

        function i(e) {
            return e && e.__esModule ? e : {default: e}
        }

        Object.defineProperty(t, "__esModule", {value: !0}), t.install = t.swiperSlide = t.swiper = t.Swiper = void 0;
        var s = n(0), r = i(s), o = n(2), a = i(o), u = n(3), p = i(u), l = window.Swiper || r.default, c = p.default,
            d = a.default, f = function (e, t) {
                t && (p.default.props.globalOptions.default = function () {
                    return t
                }), e.component(p.default.name, p.default), e.component(a.default.name, a.default)
            }, h = {Swiper: l, swiper: c, swiperSlide: d, install: f};
        t.default = h, t.Swiper = l, t.swiper = c, t.swiperSlide = d, t.install = f
    }, function (e, t, n) {
        "use strict";
        Object.defineProperty(t, "__esModule", {value: !0}), t.default = {
            name: "swiper-slide", data: function () {
                return {slideClass: "swiper-slide"}
            }, ready: function () {
                this.update()
            }, mounted: function () {
                this.update(), this.$parent && this.$parent.options && this.$parent.options.slideClass && (this.slideClass = this.$parent.options.slideClass)
            }, updated: function () {
                this.update()
            }, attached: function () {
                this.update()
            }, methods: {
                update: function () {
                    this.$parent && this.$parent.swiper && this.$parent.update()
                }
            }
        }
    }, function (e, t, n) {
        "use strict";
        Object.defineProperty(t, "__esModule", {value: !0});
        var i = n(0), s = function (e) {
            return e && e.__esModule ? e : {default: e}
        }(i), r = window.Swiper || s.default;
        "function" != typeof Object.assign && Object.defineProperty(Object, "assign", {
            value: function (e, t) {
                if (null == e) throw new TypeError("Cannot convert undefined or null to object");
                for (var n = Object(e), i = 1; i < arguments.length; i++) {
                    var s = arguments[i];
                    if (null != s) for (var r in s) Object.prototype.hasOwnProperty.call(s, r) && (n[r] = s[r])
                }
                return n
            }, writable: !0, configurable: !0
        });
        var o = ["beforeDestroy", "slideChange", "slideChangeTransitionStart", "slideChangeTransitionEnd", "slideNextTransitionStart", "slideNextTransitionEnd", "slidePrevTransitionStart", "slidePrevTransitionEnd", "transitionStart", "transitionEnd", "touchStart", "touchMove", "touchMoveOpposite", "sliderMove", "touchEnd", "click", "tap", "doubleTap", "imagesReady", "progress", "reachBeginning", "reachEnd", "fromEdge", "setTranslate", "setTransition", "resize"];
        t.default = {
            name: "swiper", props: {
                options: {
                    type: Object, default: function () {
                        return {}
                    }
                }, globalOptions: {
                    type: Object, required: !1, default: function () {
                        return {}
                    }
                }
            }, data: function () {
                return {swiper: null, classes: {wrapperClass: "swiper-wrapper"}}
            }, ready: function () {
                this.swiper || this.mountInstance()
            }, mounted: function () {
                if (!this.swiper) {
                    var e = !1;
                    for (var t in this.classes) this.classes.hasOwnProperty(t) && this.options[t] && (e = !0, this.classes[t] = this.options[t]);
                    e ? this.$nextTick(this.mountInstance) : this.mountInstance()
                }
            }, activated: function () {
                this.update()
            }, updated: function () {
                this.update()
            }, beforeDestroy: function () {
                this.$nextTick(function () {
                    this.swiper && (this.swiper.destroy && this.swiper.destroy(), delete this.swiper)
                })
            }, methods: {
                update: function () {
                    this.swiper && (this.swiper.update && this.swiper.update(), this.swiper.navigation && this.swiper.navigation.update(), this.swiper.pagination && this.swiper.pagination.render(), this.swiper.pagination && this.swiper.pagination.update())
                }, mountInstance: function () {
                    var e = Object.assign({}, this.globalOptions, this.options);
                    this.swiper = new r(this.$el, e), this.bindEvents(), this.$emit("ready", this.swiper)
                }, bindEvents: function () {
                    var e = this, t = this;
                    o.forEach(function (n) {
                        e.swiper.on(n, function () {
                            t.$emit.apply(t, [n].concat(Array.prototype.slice.call(arguments))), t.$emit.apply(t, [n.replace(/([A-Z])/g, "-$1").toLowerCase()].concat(Array.prototype.slice.call(arguments)))
                        })
                    })
                }
            }
        }
    }, function (e, t, n) {
        "use strict";
        var i = function () {
            var e = this, t = e.$createElement, n = e._self._c || t;
            return n("div", {staticClass: "swiper-container"}, [e._t("parallax-bg"), e._v(" "), n("div", {class: e.classes.wrapperClass}, [e._t("default")], 2), e._v(" "), e._t("pagination"), e._v(" "), e._t("button-prev"), e._v(" "), e._t("button-next"), e._v(" "), e._t("scrollbar")], 2)
        }, s = [], r = {render: i, staticRenderFns: s};
        t.a = r
    }, function (e, t, n) {
        "use strict";
        var i = function () {
            var e = this, t = e.$createElement;
            return (e._self._c || t)("div", {class: e.slideClass}, [e._t("default")], 2)
        }, s = [], r = {render: i, staticRenderFns: s};
        t.a = r
    }])
});