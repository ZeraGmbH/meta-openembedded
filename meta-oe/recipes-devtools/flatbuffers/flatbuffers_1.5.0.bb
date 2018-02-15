require flatbuffers.inc

DEPENDS += "flatbuffers-native"
inherit cmake

EXTRA_OECMAKE += "-DFLATBUFFERS_FLATC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/flatc \
                  -DFLATBUFFERS_BUILD_SHAREDLIB:bool=ON"

CXXFLAGS += "--std=c++11"
