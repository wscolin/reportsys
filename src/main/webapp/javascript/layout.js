var Layout = function() {
  var e = "layouts/layout/img/",
  a = "layouts/layout/css/",
  t = App.getResponsiveBreakpoint("md"),
  i = function() {
    var e, a = $(".page-content"),
    i = $(".page-sidebar"),
    s = $("body");
    if (s.hasClass("page-footer-fixed") === !0 && s.hasClass("page-sidebar-fixed") === !1) {
      var o = App.getViewPort().height - $(".page-footer").outerHeight() - $(".page-header").outerHeight();
      a.height() < o && a.attr("style", "min-height:" + o + "px")
    } else {
      if (s.hasClass("page-sidebar-fixed")) e = n(),
      s.hasClass("page-footer-fixed") === !1 && (e -= $(".page-footer").outerHeight());
      else {
        var r = $(".page-header").outerHeight(),
        d = $(".page-footer").outerHeight();
        e = App.getViewPort().width < t ? App.getViewPort().height - r - d: i.height() + 20,
        e + r + d <= App.getViewPort().height && (e = App.getViewPort().height - r - d)
      }
      a.attr("style", "min-height:" + e + "px")
    }
  },
  s = function(e, a) {
    var i = location.hash.toLowerCase(),
    s = $(".page-sidebar-menu");
    if ("click" === e || "set" === e ? a = $(a) : "match" === e && s.find("li > a").each(function() {
      var e = $(this).attr("href").toLowerCase();
      return e.length > 1 && i.substr(1, e.length - 1) == e.substr(1) ? void(a = $(this)) : void 0
    }), a && 0 != a.size() && "javascript:;" !== a.attr("href").toLowerCase() && "#" !== a.attr("href").toLowerCase()) {
      parseInt(s.data("slide-speed")),
      s.data("keep-expanded");
      s.find("li.active").removeClass("active"),
      s.find("li > a > .selected").remove(),
      s.hasClass("page-sidebar-menu-hover-submenu") === !1 ? s.find("li.open").each(function() {
        0 === $(this).children(".sub-menu").size() && ($(this).removeClass("open"), $(this).find("> a > .arrow.open").removeClass("open"))
      }) : s.find("li.open").removeClass("open"),
      a.parents("li").each(function() {
        $(this).addClass("active"),
        $(this).find("> a > span.arrow").addClass("open"),
        1 === $(this).parent("ul.page-sidebar-menu").size() && $(this).find("> a").append('<span class="selected"></span>'),
        1 === $(this).children("ul.sub-menu").size() && $(this).addClass("open")
      }),
      "click" === e && App.getViewPort().width < t && $(".page-sidebar").hasClass("in") && $(".page-header .responsive-toggler").click()
    }
  },
  o = function() {
    $(".page-sidebar-menu").on("click", "li > a.nav-toggle, li > a > span.nav-toggle",
    function(e) {
      var a = $(this).closest(".nav-item").children(".nav-link");
      if (! (App.getViewPort().width >= t && !$(".page-sidebar-menu").attr("data-initialized") && $("body").hasClass("page-sidebar-closed") && 1 === a.parent("li").parent(".page-sidebar-menu").size())) {
        var s = a.next().hasClass("sub-menu");
        if (! (App.getViewPort().width >= t && 1 === a.parents(".page-sidebar-menu-hover-submenu").size())) {
          if (s === !1) return void(App.getViewPort().width < t && $(".page-sidebar").hasClass("in") && $(".page-header .responsive-toggler").click());
          if (!a.next().hasClass("sub-menu always-open")) {
            var o = a.parent().parent(),
            n = a,
            r = $(".page-sidebar-menu"),
            d = a.next(),
            l = r.data("auto-scroll"),
            p = parseInt(r.data("slide-speed")),
            c = r.data("keep-expanded");
            c || (o.children("li.open").children("a").children(".arrow").removeClass("open"), o.children("li.open").children(".sub-menu:not(.always-open)").slideUp(p), o.children("li.open").removeClass("open"));
            var h = -200;
            d.is(":visible") ? ($(".arrow", n).removeClass("open"), n.parent().removeClass("open"), d.slideUp(p,
            function() {
              l === !0 && $("body").hasClass("page-sidebar-closed") === !1 && ($("body").hasClass("page-sidebar-fixed") ? r.slimScroll({
                scrollTo: n.position().top
              }) : App.scrollTo(n, h)),
              i()
            })) : s && ($(".arrow", n).addClass("open"), n.parent().addClass("open"), d.slideDown(p,
            function() {
              l === !0 && $("body").hasClass("page-sidebar-closed") === !1 && ($("body").hasClass("page-sidebar-fixed") ? r.slimScroll({
                scrollTo: n.position().top
              }) : App.scrollTo(n, h)),
              i()
            })),
            e.preventDefault()
          }
        }
      }
    }),
    App.isAngularJsApp() && $(".page-sidebar-menu li > a").on("click",
    function(e) {
      App.getViewPort().width < t && $(this).next().hasClass("sub-menu") === !1 && $(".page-header .responsive-toggler").click()
    }),
    $(".page-sidebar").on("click", " li > a.ajaxify",
    function(e) {
      e.preventDefault(),
      App.scrollTop();
      var a = $(this).attr("href"),
      resTitle = this.innerHTML,
      //resTitle = $(this).attr("title"),
      resName = $(this).attr("data-name"),
      i = $(".page-sidebar ul"),
      s = ($(".page-content"), $(".page-content .page-content-body"));  
      i.children("li.active").removeClass("active"),
      i.children("arrow.open").removeClass("open"),
      $(this).parents("li").each(function() {
        $(this).addClass("active"),
        $(this).children("a > span.arrow").addClass("open")
      }),
      $(this).parents("li").addClass("active"),
      App.getViewPort().width < t && $(".page-sidebar").hasClass("in") && $(".page-header .responsive-toggler").click(),
      App.startPageLoading();
      //标题
      //document.getElementById('page-bar-title').innerHTML = resTitle;
      var o = $(this);
      $.ajax({
        type: "GET",
        cache: !1,
        url: a,
        dataType: "html",
        success: function(res) {
          0 === o.parents("li.open").size() && $(".page-sidebar-menu > li.open > a").click();
          App.stopPageLoading();
          var pageContent = $(res);
          var buttons = pageContent.find("[data-name]");
          buttons.each(function (index, res) {
      		//var permission = checkPermission(resName, $(el).attr("data-name"));
        	//alert(resName); 
        	//	if (permission == false) {
        	//	$(el).addClass("hidden");
        	//	}
        	var permission = $(res).attr("data-name"); 
        	if (permission == "add1") {
        		$(res).addClass("hidden");
           	}
          });          
          s.html(pageContent);
          Layout.fixContentHeight();
          App.initAjax();         
        },
        error: function(e, a, t) {
          App.stopPageLoading(),
          s.html("<h4>没有找到页面！</h4>")
        }
      })
    }),
    $(".page-content").on("click", ".ajaxify",
    function(e) {
      e.preventDefault(),
      App.scrollTop();
      var a = $(this).attr("href"),
      i = ($(".page-content"), $(".page-content .page-content-body"));
      App.startPageLoading(),
      App.getViewPort().width < t && $(".page-sidebar").hasClass("in") && $(".page-header .responsive-toggler").click(),
      $.ajax({
        type: "GET",
        cache: !1,
        url: a,
        dataType: "html",
        success: function(e) {
          App.stopPageLoading(),
          i.html(e),
          Layout.fixContentHeight(),
          App.initAjax()
        },
        error: function(e, a, t) {
          i.html("<h4>Could not load the requested content.</h4>"),
          App.stopPageLoading()
        }
      })
    }),
    $(document).on("click", ".page-header-fixed-mobile .page-header .responsive-toggler",
    function() {
      App.scrollTop()
    }),
    d(),
    $(".page-sidebar").on("click", ".sidebar-search .remove",
    function(e) {
      e.preventDefault(),
      $(".sidebar-search").removeClass("open")
    }),
    $(".page-sidebar .sidebar-search").on("keypress", "input.form-control",
    function(e) {
      return 13 == e.which ? ($(".sidebar-search").submit(), !1) : void 0
    }),
    $(".sidebar-search .submit").on("click",
    function(e) {
      e.preventDefault(),
      $("body").hasClass("page-sidebar-closed") && $(".sidebar-search").hasClass("open") === !1 ? (1 === $(".page-sidebar-fixed").size() && $(".page-sidebar .sidebar-toggler").click(), $(".sidebar-search").addClass("open")) : $(".sidebar-search").submit()
    }),
    0 !== $(".sidebar-search").size() && ($(".sidebar-search .input-group").on("click",
    function(e) {
      e.stopPropagation()
    }), $("body").on("click",
    function() {
      $(".sidebar-search").hasClass("open") && $(".sidebar-search").removeClass("open")
    }))
  },
  n = function() {
    var e = App.getViewPort().height - $(".page-header").outerHeight(!0);
    return $("body").hasClass("page-footer-fixed") && (e -= $(".page-footer").outerHeight()),
    e
  },
  r = function() {
    var e = $(".page-sidebar-menu");
    return App.destroySlimScroll(e),
    0 === $(".page-sidebar-fixed").size() ? void i() : void(App.getViewPort().width >= t && (e.attr("data-height", n()), App.initSlimScroll(e), i()))
  },
  d = function() {
    var e = $("body");
    e.hasClass("page-sidebar-fixed") && $(".page-sidebar").on("mouseenter",
    function() {
      e.hasClass("page-sidebar-closed") && $(this).find(".page-sidebar-menu").removeClass("page-sidebar-menu-closed")
    }).on("mouseleave",
    function() {
      e.hasClass("page-sidebar-closed") && $(this).find(".page-sidebar-menu").addClass("page-sidebar-menu-closed")
    })
  },
  l = function() {
    var e = $("body");
    $.cookie && "1" === $.cookie("sidebar_closed") && App.getViewPort().width >= t && ($("body").addClass("page-sidebar-closed"), $(".page-sidebar-menu").addClass("page-sidebar-menu-closed")),
    $("body").on("click", ".sidebar-toggler",
    function(a) {
      var t = $(".page-sidebar"),
      i = $(".page-sidebar-menu");
      $(".sidebar-search", t).removeClass("open"),
      e.hasClass("page-sidebar-closed") ? (e.removeClass("page-sidebar-closed"), i.removeClass("page-sidebar-menu-closed"), $.cookie && $.cookie("sidebar_closed", "0")) : (e.addClass("page-sidebar-closed"), i.addClass("page-sidebar-menu-closed"), e.hasClass("page-sidebar-fixed") && i.trigger("mouseleave"), $.cookie && $.cookie("sidebar_closed", "1")),
      $(window).trigger("resize")
    })
  },
  p = function() {
    $(".page-header").on("click", '.hor-menu a[data-toggle="tab"]',
    function(e) {
      e.preventDefault();
      var a = $(".hor-menu .nav"),
      t = a.find("li.current");
      $("li.active", t).removeClass("active"),
      $(".selected", t).remove();
      var i = $(this).parents("li").last();
      i.addClass("current"),
      i.find("a:first").append('<span class="selected"></span>')
    }),
    $(".page-header").on("click", ".search-form",
    function(e) {
      $(this).addClass("open"),
      $(this).find(".form-control").focus(),
      $(".page-header .search-form .form-control").on("blur",
      function(e) {
        $(this).closest(".search-form").removeClass("open"),
        $(this).unbind("blur")
      })
    }),
    $(".page-header").on("keypress", ".hor-menu .search-form .form-control",
    function(e) {
      return 13 == e.which ? ($(this).closest(".search-form").submit(), !1) : void 0
    }),
    $(".page-header").on("mousedown", ".search-form.open .submit",
    function(e) {
      e.preventDefault(),
      e.stopPropagation(),
      $(this).closest(".search-form").submit()
    }),
    $('[data-hover="megamenu-dropdown"]').not(".hover-initialized").each(function() {
      $(this).dropdownHover(),
      $(this).addClass("hover-initialized")
    }),
    $(document).on("click", ".mega-menu-dropdown .dropdown-menu",
    function(e) {
      e.stopPropagation()
    })
  },
  c = function() {
    $("body").on("shown.bs.tab", 'a[data-toggle="tab"]',
    function() {
      i()
    })
  },
  h = function() {
    var e = 300,
    a = 500;
    navigator.userAgent.match(/iPhone|iPad|iPod/i) ? $(window).bind("touchend touchcancel touchleave",
    function(t) {
      $(this).scrollTop() > e ? $(".scroll-to-top").fadeIn(a) : $(".scroll-to-top").fadeOut(a)
    }) : $(window).scroll(function() {
      $(this).scrollTop() > e ? $(".scroll-to-top").fadeIn(a) : $(".scroll-to-top").fadeOut(a)
    }),
    $(".scroll-to-top").click(function(e) {
      return e.preventDefault(),
      $("html, body").animate({
        scrollTop: 0
      },
      a),
      !1
    })
  },
  g = function() {
    $(".full-height-content").each(function() {
      var e, a = $(this);
      if (e = App.getViewPort().height - $(".page-header").outerHeight(!0) - $(".page-footer").outerHeight(!0) - $(".page-title").outerHeight(!0) - $(".page-bar").outerHeight(!0), a.hasClass("portlet")) {
        var i = a.find(".portlet-body");
        App.destroySlimScroll(i.find(".full-height-content-body")),
        e = e - a.find(".portlet-title").outerHeight(!0) - parseInt(a.find(".portlet-body").css("padding-top")) - parseInt(a.find(".portlet-body").css("padding-bottom")) - 5,
        App.getViewPort().width >= t && a.hasClass("full-height-content-scrollable") ? (e -= 35, i.find(".full-height-content-body").css("height", e), App.initSlimScroll(i.find(".full-height-content-body"))) : i.css("min-height", e)
      } else App.destroySlimScroll(a.find(".full-height-content-body")),
      App.getViewPort().width >= t && a.hasClass("full-height-content-scrollable") ? (e -= 35, a.find(".full-height-content-body").css("height", e), App.initSlimScroll(a.find(".full-height-content-body"))) : a.css("min-height", e)
    })
  };
  return {
    initHeader: function() {
      p()
    },
    setSidebarMenuActiveLink: function(e, a) {
      s(e, a)
    },
    initSidebar: function() {
      r(),
      o(),
      l(),
      App.isAngularJsApp() && s("match"),
      App.addResizeHandler(r)
    },
    initContent: function() {
      g(),
      c(),
      App.addResizeHandler(i),
      App.addResizeHandler(g)
    },
    initFooter: function() {
      h()
    },
    init: function() {
      this.initHeader(),
      this.initSidebar(),
      this.initContent(),
      this.initFooter()
    },
    fixContentHeight: function() {
      i()
    },
    initFixedSidebarHoverEffect: function() {
      d()
    },
    initFixedSidebar: function() {
      r()
    },
    getLayoutImgPath: function() {
      return App.getAssetsPath() + e
    },
    getLayoutCssPath: function() {
      return App.getAssetsPath() + a
    }
  }
} ();
App.isAngularJsApp() === !1 && jQuery(document).ready(function() {
  Layout.init()
});