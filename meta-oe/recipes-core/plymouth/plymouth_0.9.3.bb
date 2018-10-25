SUMMARY = "Plymouth is a project from Fedora providing a flicker-free graphical boot process."

DESCRIPTION = "Plymouth is an application that runs very early in the boot process \
    (even before the root filesystem is mounted!) that provides a \
    graphical boot animation while the boot process happens in the background. \
"

HOMEPAGE = "http://www.freedesktop.org/wiki/Software/Plymouth"
SECTION = "base"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = "libcap libpng cairo dbus udev"
PROVIDES = "virtual/psplash"
RPROVIDES_${PN} = "virtual-psplash virtual-psplash-support"

SRC_URI = " \
    http://www.freedesktop.org/software/plymouth/releases/${BPN}-${PV}.tar.xz \
    file://0001-Make-full-path-to-systemd-tty-ask-password-agent-con.patch \
"
SRC_URI[md5sum] = "b261c720888a5431cdfce8494805eab3"
SRC_URI[sha256sum] = "9f8dd08a90ceaf6228dcd8c27759adf18fc9482f15b6c56dcbcced268b4e4a74"

EXTRA_OECONF += " --enable-shared --disable-static --disable-gtk --disable-documentation \
    --with-logo=${LOGO} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '--enable-systemd-integration --with-system-root-install --with-systemd-tty-ask-password-agent=${base_bindir}/systemd-tty-ask-password-agent', '', d)} \
"

PACKAGECONFIG ??= "pango initrd"
PACKAGECONFIG_append_x86 = " drm"
PACKAGECONFIG_append_x86-64 = " drm"

PACKAGECONFIG[drm] = "--enable-drm,--disable-drm,libdrm"
PACKAGECONFIG[pango] = "--enable-pango,--disable-pango,pango"
PACKAGECONFIG[gtk] = "--enable-gtk,--disable-gtk,gtk+3"
PACKAGECONFIG[initrd] = ",,,"

LOGO ??= "${datadir}/plymouth/bizcom.png"

inherit autotools pkgconfig systemd

do_install_append() {
    # Remove /var/run from package as plymouth will populate it on startup
    rm -fr "${D}${localstatedir}/run"

    if ! ${@bb.utils.contains('PACKAGECONFIG', 'initrd', 'true', 'false', d)}; then
        rm -rf "${D}${libexecdir}"
    fi
}

PACKAGES =. "${@bb.utils.contains('PACKAGECONFIG', 'initrd', '${PN}-initrd ', '', d)}"
PACKAGES =+ "${PN}-set-default-theme"

FILES_${PN}-initrd = "${libexecdir}/plymouth/*"
FILES_${PN}-set-default-theme = "${sbindir}/plymouth-set-default-theme"

FILES_${PN} += "${systemd_unitdir}/system/*"
FILES_${PN}-dbg += "${libdir}/plymouth/renderers/.debug"


RDEPENDS_${PN}-initrd = "bash dracut"
RDEPENDS_${PN}-set-default-theme = "bash"

SYSTEMD_SERVICE_${PN} = "plymouth-start.service"
