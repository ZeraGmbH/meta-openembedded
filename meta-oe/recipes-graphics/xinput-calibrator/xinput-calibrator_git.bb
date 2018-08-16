DESCRIPTION = "A generic touchscreen calibration program for X.Org"
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/xinput_calibrator"
LICENSE = "MIT-X"
LIC_FILES_CHKSUM = "file://src/calibrator.cpp;endline=22;md5=1bcba08f67cdb56f34021557898e4b5a"
DEPENDS = "virtual/libx11 libxi"

PV = "0.7.5+git${SRCPV}"
PR = "r4"

inherit autotools

SRCREV = "e02de96acbe2fa5a57b0bda6821a053b1b123fd2"
SRC_URI = " \
    git://github.com/tias/xinput_calibrator.git;protocol=git \
    file://0001-Add-parameter-output-filename-and-store-in-output_fi.patch \
    file://0002-CalibratorEvdev-do-write-calibration-results-if-outp.patch \
    file://0003-CalibratorXorgPrint-do-write-calibration-results-if-.patch \
    file://0004-CalibratorUsbtouchscreen-overridde-default-file-name.patch \
    file://0005-Make-all-Calibrator-destructors-virtual-to-fix-warni.patch \
    file://0006-Set-up-buffer-size-for-max-line-length-only-once-in-.patch \
    file://xinput_calibrator_pointercal2.sh \
    file://99-ADS7846_Touchscreen_.conf \
"

S = "${WORKDIR}/git"

# force native X11 ui as we don't have gtk+ in DEPENDS
EXTRA_OECONF += "--with-gui=x11"

do_install_append() {
        install -d ${D}${bindir}
        install -m 0755 ${WORKDIR}/xinput_calibrator_pointercal2.sh ${D}${bindir}/xinput_calibrator_pointercal2.sh
        install -d ${D}/${sysconfdir}/X11/xorg.conf.d
        install -m 0666 ${WORKDIR}/99-ADS7846_Touchscreen_.conf ${D}/${sysconfdir}/X11/xorg.conf.d
        sed -i 's,xinput_calibrator;,xinput_calibrator_pointercal2.sh;,g' \
		${D}${datadir}/applications/xinput_calibrator.desktop
}

RRECOMMENDS_${PN} = "pointercal-xinput"

pkg_postinst_${PN} () {
    for calfile in `find $D/etc/X11/xorg.conf.d -name '99-*.conf'` ; do
        chmod 666 $calfile
    done
}

CONFFILES_${PN} = "${sysconfdir}/X11/xorg.conf.d/99-ADS7846_Touchscreen_.conf"
