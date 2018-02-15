SUMMARY = "Flatbuffers - structured data serialisation mechanism"
DESCRIPTION = "Flatbuffers are a way of encoding structured data in an \
efficient yet extensible format similar to Protocol Buffers."
HOMEPAGE = "https://google.github.com/flatbuffers"
SECTION = "console/tools"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=a873c5645c184d51e0f9b34e1d7cf559"

SRCREV = "b29ba4c70c7b8ef415e37809574ac74a9bf0b632"

PV = "1.5.0+git${SRCPV}"

SRC_URI = "git://github.com/google/flatbuffers.git"
S = "${WORKDIR}/git"
