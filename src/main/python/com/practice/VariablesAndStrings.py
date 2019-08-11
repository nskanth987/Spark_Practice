def isTrue():
    return True


def main():
    names = ['a', 2, 3, 4.5, 'b']
    names = names[2:]
    for name in names:
        print(name)


if __name__ == '__main__':
    main()
