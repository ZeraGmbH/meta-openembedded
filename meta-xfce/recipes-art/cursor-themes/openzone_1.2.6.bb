SUMMARY = "X11 Mouse Theme"
HOMEPAGE = "http://xfce-look.org/content/show.php/OpenZone?content=111343"
SECTION = "x11/wm"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=15c85787ab411af36a78dfc20fdf7793"

inherit allarch pixbufcache

DEPENDS = "icon-slicer-native libpng-native gdk-pixbuf-native"

SRC_URI = "git://github.com/ducakar/openzone-cursors.git"
SRCREV = "fc18e146a6c7ca7b6edb9df0b81ea1ddb22113c2"
S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${datadir}/icons
    for theme in `find -name '*.tar.xz'`; do
        tar -Jxf ${theme} -C ${D}${datadir}/icons
    done
}

python populate_packages_prepend () {
    icondir = bb.data.expand('${datadir}/icons', d)
    do_split_packages(d, icondir, '^(.*)', '%s', 'Open Zone cursors %s', allow_dirs=True)
}

PACKAGES_DYNAMIC += "^openzone-.*"
ALLOW_EMPTY_${PN} = "1"
