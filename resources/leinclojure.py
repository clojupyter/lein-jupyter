#!/usr/bin/env python3
import os
import sys


def main(lein_working_directory, argv):
    os.chdir(lein_working_directory)
    os.execvp('lein', ['lein', 'jupyter', 'kernel'] + argv)

if __name__ == '__main__':
    if 'LEIN_WORKING_DIRECTORY' not in os.environ:
        raise RuntimeError("The environment variable 'LEIN_WORKING_DIRECTORY'"
                           " for this kernel to run")
    main(os.environ['LEIN_WORKING_DIRECTORY'], sys.argv[1:])
